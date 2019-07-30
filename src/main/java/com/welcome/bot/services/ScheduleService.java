package com.welcome.bot.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.models.ScheduleDTO;
import com.welcome.bot.repository.MessageRepository;
import com.welcome.bot.repository.ScheduleRepository;



@Service
public class ScheduleService {

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	public ResponseEntity<Schedule> createSchedule(ScheduleDTO scheduleModel) {
		Message message = messageRepository.findById(scheduleModel.getMessageId()).orElseThrow();
		Schedule schedule = new Schedule(scheduleModel.isActive(), scheduleModel.isRepeat(), scheduleModel.getRunAt(), message);
		scheduleRepository.save(schedule);
		return ResponseEntity.ok().body(schedule);
	}
	
	public Page<Schedule> getAllSchedules(Pageable pageable) {
		Page<Schedule> schedulesPage = scheduleRepository.findAll(pageable);
		return schedulesPage;
	}
	
	public Schedule getSchedule(@PathVariable Integer scheduleId) {
		Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
		return schedule;
	}
	
	public Schedule updateSchedule(@PathVariable Integer scheduleId, @RequestBody ScheduleDTO scheduleModel) {
		Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
		schedule.setActive(scheduleModel.isActive());
		schedule.setRunAt(scheduleModel.getRunAt());
		schedule.setRepeat(scheduleModel.isRepeat());
		if(schedule.getMessage().getMessageId() != scheduleModel.getMessageId()) {
			Message message = messageRepository.findById(scheduleModel.getMessageId()).orElseThrow();
			schedule.setMessage(message);
		}
		return schedule;
	}
	
	public ResponseEntity<Schedule> deleteSchedule(@PathVariable Integer scheduleId) {
		scheduleRepository.deleteById(scheduleId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}


