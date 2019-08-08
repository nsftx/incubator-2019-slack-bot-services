package com.welcome.bot.services;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.exception.ScheduleNotFoundException;
import com.welcome.bot.exception.ScheduleValidationException;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.ScheduleDTO;
import com.welcome.bot.models.TriggerDTO;
import com.welcome.bot.models.ScheduleCreateDTO;
import com.welcome.bot.repository.MessageRepository;
import com.welcome.bot.repository.ScheduleRepository;
import com.welcome.bot.slack.api.SlackClientApi;

import ch.qos.logback.classic.turbo.TurboFilter;


@Service
public class ScheduleService {

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	SlackClientApi slackClientApi;
	
	//creates schedule
	public ScheduleDTO createSchedule(ScheduleCreateDTO scheduleModel) {
		Message message = messageRepository.findById(scheduleModel.getMessageId()).orElseThrow();
		
		Schedule schedule = new Schedule(scheduleModel.isActive(),
										scheduleModel.isRepeat(),
										scheduleModel.getRunAt(), 
										scheduleModel.getChannel(), 
										message);
		
		//throw exception if not validate
		validateDate(scheduleModel);
		
		//if schedule is active we send it to slack
		if(schedule.getActive()) {
			sendScheduleToSlackApi(schedule);	
		}
		

		scheduleRepository.save(schedule);
		
		//mapping to dto 
		ScheduleDTO scheduleContentDTO = convertToDto(schedule);
		
		return scheduleContentDTO;
	}
	
	//get all schedules
	public Page<ScheduleDTO> getAllSchedules(Pageable pageable) {
		Page<Schedule> schedulesPage = scheduleRepository.findAll(pageable);

		List<Schedule> scheduleList = schedulesPage.getContent();
		
		List<ScheduleDTO> scheduleContentDTOlist = new ArrayList<>();
		for (Schedule schedule : scheduleList) {
			ScheduleDTO scheduleDTO = convertToDto(schedule);
			scheduleContentDTOlist.add(scheduleDTO);
		}
		
		Page<ScheduleDTO> scheduleContentDTOPage = new PageImpl<ScheduleDTO>(scheduleContentDTOlist, pageable, schedulesPage.getTotalElements());
		return scheduleContentDTOPage;
	}
	
	public ScheduleDTO getSchedule(@PathVariable Integer scheduleId) {
		Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
		ScheduleDTO scheduleContentDTO = convertToDto(schedule);
		return scheduleContentDTO;
	}
	
	public ScheduleDTO updateSchedule(@PathVariable Integer scheduleId, @RequestBody ScheduleCreateDTO scheduleModel) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new ScheduleNotFoundException(scheduleId));
		
		schedule.setActive(scheduleModel.isActive());
		schedule.setRunAt(scheduleModel.getRunAt());
		schedule.setRepeat(scheduleModel.isRepeat());
		schedule.setChannel(scheduleModel.getChannel());
		
		
		if(schedule.getActive()) {
			boolean deleted = deleteScheduleInSlackApi(schedule);
			if(deleted) {
				System.out.println("Schedual is delete from slack api");
			}
			sendScheduleToSlackApi(schedule);	
			System.out.println("Schedule is updated from slack api");
		}
		
		if(schedule.getMessage().getMessageId() != scheduleModel.getMessageId()) {
			Message message = messageRepository.findById(scheduleModel.getMessageId()).orElseThrow();
			schedule.setMessage(message);
		}
		scheduleRepository.save(schedule);
		ScheduleDTO scheduleContentDTO = convertToDto(schedule);
		return scheduleContentDTO;
	}
	
	public ResponseEntity<Schedule> deleteSchedule(@PathVariable Integer scheduleId) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule data for id: " + scheduleId + "not found"));
		
		if(schedule.getActive()) {
			boolean deleted = deleteScheduleInSlackApi(schedule);	
			if(deleted) {
				System.out.println("Schedual is delete from slack api");
			}
		}

		scheduleRepository.deleteById(scheduleId);
		System.out.println("deleted from database");
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	// deletes schedules in database and in slack
	public void deleteAllSchedulesByMessage(Message message) {
		List<Schedule> scheduleList = scheduleRepository.findAllByMessage(message);
		for (Schedule schedule : scheduleList) {
			//if active we have to delete it in slack
			if(schedule.getActive()) {
				deleteScheduleInSlackApi(schedule);
			}
			scheduleRepository.delete(schedule);
		}
	}
	
	//delete all triggers by list you send as parameter
	public void deleteAllSchedulesByList(List<Schedule> scheduleList) {
		if(!scheduleList.isEmpty()) {
			scheduleRepository.deleteAll(scheduleList);	
		}
	}
	
	//convert entity to model
	private ScheduleDTO convertToDto(Schedule schedule) {
		ScheduleDTO scheduleContentDTO = modelMapper.map(schedule, ScheduleDTO.class);
		scheduleContentDTO.setMessageDto(modelMapper.map(schedule.getMessage(), MessageDTO.class));
	    return scheduleContentDTO;
	}
	
	//validation for date
	private void validateDate(ScheduleCreateDTO scheduleModel) throws ScheduleValidationException{
		Date trenutniDatum = new Date();
		Integer dateStatus = scheduleModel.getRunAt().compareTo(trenutniDatum);
		if(dateStatus == -1) {
			throw new ScheduleValidationException(scheduleModel.getRunAt(), trenutniDatum);
		}
	}
	
	
	//SLACK API methods
	
	//sending schedule to slack
	public void sendScheduleToSlackApi(Schedule schedule) {
		String slackMessageId = "";
		slackMessageId = slackClientApi.createSchedule("#general", schedule.getMessage().getText(), schedule.getRunAt(), schedule.getRepeat());
		System.out.println(schedule.getRunAt());
		
		if(slackMessageId != null && !slackMessageId.isEmpty()) {
			schedule.setSlackMessageId(slackMessageId);
		}
	}
	
	//deletes scheduels in slack api
	public boolean deleteScheduleInSlackApi(Schedule schedule) {
		String slackMessageId = schedule.getSlackMessageId();
		boolean status = false;
		if(slackMessageId != null && !slackMessageId.isEmpty()) {
			status = slackClientApi.deleteSchedule(schedule.getSlackMessageId(), "#general", schedule.getRepeat());		
		}
		return status;
	}

	public List<String> getChannelsList() {
		return slackClientApi.getChannelsList();
	}
	
	public List<Schedule> getAllByChannel(String channel){
		List<Schedule> scheduleList = scheduleRepository.findAllByChannel(channel);
		return scheduleList;
	}
	
	public void updateActiveStatus(List<Schedule> listSchedule, boolean active) {
		for (Schedule schedule : listSchedule) {
			schedule.setActive(active);
			scheduleRepository.save(schedule);
		}
	}

	
}


