package com.welcome.bot.services;





import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Matchers.booleanThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.json.JSONException;
import org.junit.validator.PublicClassValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ejb.access.EjbAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.exception.base.BaseException;
import com.welcome.bot.exception.message.MessageNotFoundException;
import com.welcome.bot.exception.schedule.ScheduleNotFoundException;
import com.welcome.bot.exception.schedule.ScheduleValidationException;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.ScheduleDTO;
import com.welcome.bot.models.TriggerDTO;
import com.welcome.bot.models.ScheduleCreateDTO;
import com.welcome.bot.repository.MessageRepository;
import com.welcome.bot.repository.ScheduleRepository;
import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.customexceptionhandler.SlackApiException;
import com.welcome.bot.slack.api.model.interactionpayload.Channel;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;



@Service
public class ScheduleService {

	MessageRepository messageRepository;
	
	ScheduleRepository scheduleRepository;
	
	ModelMapper modelMapper;
	
	SlackClientApi slackClientApi;
	
	@Autowired
	public ScheduleService(final MessageRepository messageRepository, 
			final ScheduleRepository scheduleRepository,
			final ModelMapper modelMapper, 
			final SlackClientApi slackClientApi) {
		this.messageRepository = messageRepository;
		this.scheduleRepository = scheduleRepository;
		this.modelMapper = modelMapper;
		this.slackClientApi = slackClientApi;
	}

	//creates schedule
	public ScheduleDTO createSchedule(ScheduleCreateDTO scheduleModel) {
		Message message = messageRepository.findById(scheduleModel.getMessageId())
				.orElseThrow(() -> new MessageNotFoundException(scheduleModel.getMessageId()));
		
		//throw exception if not validate
		try {
			validateDate(scheduleModel);
		} catch (ScheduleValidationException e) {
			throw e;
		}

		String intervalType = null;
		if(scheduleModel.isRepeat()) {
			intervalType = getIntervalType(scheduleModel);
		}
		String channel = null;
		try {
			 channel = getChannelById(scheduleModel.getChannelId());
		} catch (JSONException e) {
			throw new BaseException("Channel not found");
		}


		Schedule schedule = new Schedule(scheduleModel.isActive(),
										scheduleModel.isRepeat(),
										intervalType,
										scheduleModel.getRunAt(), 
										channel, 
										message);
		
		//if schedule is active we send it to slack
		if(schedule.getActive()) {
			try {
				sendScheduleToSlackApi(schedule);
			} catch (Exception e) {
				e.printStackTrace();
			}	
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
		Schedule schedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new ScheduleNotFoundException(scheduleId));
		ScheduleDTO scheduleContentDTO = convertToDto(schedule);
		return scheduleContentDTO;
	}
	
	public ScheduleDTO updateSchedule(Integer scheduleId, boolean active) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new ScheduleNotFoundException(scheduleId));		
		
		boolean lastState = schedule.getActive();
		
		if(lastState == false && active == true) {
			deleteScheduleInSlackApi(schedule);
			try {
				sendScheduleToSlackApi(schedule);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		schedule.setActive(active);	
		scheduleRepository.save(schedule);
		
		ScheduleDTO scheduleContentDTO = convertToDto(schedule);
		return scheduleContentDTO;
	}
	
	public ResponseEntity<Schedule> deleteSchedule(Integer scheduleId) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule data for id: " + scheduleId + "not found"));
		
		if(schedule.getActive()) {
			deleteScheduleInSlackApi(schedule);	
		}

		softDeleteSchedule(schedule);
		
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
		for (Schedule schedule : scheduleList) {
			softDeleteSchedule(schedule);
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
	public void sendScheduleToSlackApi(Schedule schedule) throws Exception {
		String slackMessageId = "";
		//slackMessageId = slackClientApi.createSchedule("#general", schedule.getMessage().getText(), schedule.getRunAt(), schedule.getRepeat());
		try {
			slackMessageId = slackClientApi.createSchedule(schedule.getChannel(), schedule.getMessage().getText(), schedule.getRunAt(), schedule.getIntervalType());
		} catch (Exception e) {
			throw e;
		}
		
		if(slackMessageId != null && !slackMessageId.isEmpty()) {
			schedule.setSlackScheduleId(slackMessageId);
		}
	}
	
	//deletes scheduels in slack api
	public boolean deleteScheduleInSlackApi(Schedule schedule) {
		String slackMessageId = schedule.getSlackScheduleId();
		boolean status = false;
		if(slackMessageId != null && !slackMessageId.isEmpty()) {
			try {
				slackClientApi.deleteSchedule(schedule.getSlackScheduleId(), schedule.getChannel());
			} catch (SlackApiException e) {
				e.printStackTrace();
			}		
		}
		return status;
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

	public JSONArray getRepeatIntervals() {

		JSONArray intervalList = new JSONArray();

		JSONObject daily = new JSONObject();
		daily.put("id", "1");
		daily.put("type", "DAILY");
		
		JSONObject weekly = new JSONObject();
		weekly.put("id", "2");
		weekly.put("type", "WEEKLY");
		
		JSONObject monthly = new JSONObject();
		monthly.put("id", "3");
		monthly.put("type", "MONTHLY");
		
		intervalList.add(daily);
		intervalList.add(weekly);
		intervalList.add(monthly);

		for (Object object : intervalList) {
			JSONObject jsonObject = (JSONObject) object;
			System.out.println(jsonObject.get("type"));
		}
		return intervalList;
	}
	
	public String getIntervalType(ScheduleCreateDTO scheduleModel) throws NullPointerException{
		String intervalType = null;
		JSONArray jsonArray = getRepeatIntervals();
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			if(jsonObject.get("id").toString().equals(scheduleModel.getIntervalId())) {
				intervalType = jsonObject.get("type").toString();
				break;
			}
		}
		if(intervalType == null) {
			throw new NullPointerException();
		}else {
			return intervalType;	
		}
	}
	
	private String getChannelById(String channelId) {
		List<Channel> list = slackClientApi.getChannelsList();
		for (Channel channel : list) {
			if(channel.getId().equals(channelId)) {
				return channel.getName();
			}
		}
		return null;
	}
	
	private void softDeleteSchedule(Schedule schedule) {
		schedule.setDeleted(true);
		scheduleRepository.save(schedule);
	}
}


