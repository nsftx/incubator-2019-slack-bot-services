package com.services;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.domain.Message;
import com.domain.Schedule;
import com.domain.Trigger;
import com.models.ScheduleDTO;
import com.repository.MessageRepository;
import com.repository.ScheduleRepository;


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
		if(schedule.getMessage().getId() != scheduleModel.getMessageId()) {
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



//System.out.println(scheduleModel.getRunAt());
//System.out.println(":::::::::::::::::::::::::::::::");
//Date date = new Date();
//System.out.println(date);
//System.out.println(":::::::::::::::::::::::::::::::");
//ZonedDateTime zdt = Instant.parse("2018-07-17T12:16:50.52Z")
//        .atZone(ZoneId.of("Europe/Berlin"));
//System.out.println(zdt.toLocalDate());
//System.out.println(zdt.toString());
//System.out.println(":::::::::::::::::::::::::::::::");
//LocalDate date2 = zdt.toLocalDate();
//System.out.println(date2);

//System.out.println("::::::::::::::::::::");
//System.out.println(convertToLocalDateViaInstant(date));
//System.out.println(convertToLocalDateTimeViaInstant(date));
//System.out.println(":::::::::::::::::::::::::::::::");
//
//public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
//    return dateToConvert.toInstant()
//      .atZone(ZoneId.systemDefault())
//      .toLocalDate();
//}
//
//public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
//    return dateToConvert.toInstant()
//      .atZone(ZoneId.systemDefault())
//      .toLocalDateTime();
//}