package com.welcome.bot.services;



import java.util.ArrayList;
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

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.ScheduleContentDTO;
import com.welcome.bot.models.ScheduleDTO;
import com.welcome.bot.repository.MessageRepository;
import com.welcome.bot.repository.ScheduleRepository;


@Service
public class ScheduleService {

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public ScheduleContentDTO createSchedule(ScheduleDTO scheduleModel) {
		Message message = messageRepository.findById(scheduleModel.getMessageId()).orElseThrow();
		
		Schedule schedule = new Schedule(scheduleModel.isActive(),
										scheduleModel.isRepeat(), 
										scheduleModel.getRunAt(), 
										message);
		
		scheduleRepository.save(schedule);
		
		//mapping to dto
		ScheduleContentDTO scheduleContentDTO = convertToContentDto(schedule);
		
		return scheduleContentDTO;
	}
	
	public Page<ScheduleContentDTO> getAllSchedules(Pageable pageable) {
		Page<Schedule> schedulesPage = scheduleRepository.findAll(pageable);

		List<Schedule> scheduleList = schedulesPage.getContent();
		
		List<ScheduleContentDTO> scheduleContentDTOlist = new ArrayList<>();
		for(int i = 0; i < schedulesPage.getTotalElements(); i++) {
			Schedule schedule = scheduleList.get(i);
			ScheduleContentDTO scheduleContentDTO = convertToContentDto(schedule);
			scheduleContentDTOlist.add(scheduleContentDTO);
		}
		
		Page<ScheduleContentDTO> scheduleContentDTOPage = new PageImpl<ScheduleContentDTO>(scheduleContentDTOlist, pageable, schedulesPage.getTotalElements());
		return scheduleContentDTOPage;
	}
	
	public ScheduleContentDTO getSchedule(@PathVariable Integer scheduleId) {
		Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
		ScheduleContentDTO scheduleContentDTO = convertToContentDto(schedule);
		return scheduleContentDTO;
	}
	
	public ScheduleContentDTO updateSchedule(@PathVariable Integer scheduleId, @RequestBody ScheduleDTO scheduleModel) {
		Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
		schedule.setActive(scheduleModel.isActive());
		schedule.setRunAt(scheduleModel.getRunAt());
		schedule.setRepeat(scheduleModel.isRepeat());
		
		
		if(schedule.getMessage().getMessageId() != scheduleModel.getMessageId()) {
			Message message = messageRepository.findById(scheduleModel.getMessageId()).orElseThrow();
			schedule.setMessage(message);
		}
		
		ScheduleContentDTO scheduleContentDTO = convertToContentDto(schedule);
		
		return scheduleContentDTO;
	}
	
	public ResponseEntity<Schedule> deleteSchedule(@PathVariable Integer scheduleId) {
		scheduleRepository.deleteById(scheduleId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}	
		
	private ScheduleContentDTO convertToContentDto(Schedule schedule) {
		ScheduleContentDTO scheduleContentDTO = modelMapper.map(schedule, ScheduleContentDTO.class);
		scheduleContentDTO.setMessageDto(modelMapper.map(schedule.getMessage(), MessageDTO.class));
	    return scheduleContentDTO;
	}
	
	
}


