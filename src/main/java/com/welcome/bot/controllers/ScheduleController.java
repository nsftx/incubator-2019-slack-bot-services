package com.welcome.bot.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.domain.Schedule;
import com.welcome.bot.models.ScheduleContentDTO;
import com.welcome.bot.models.ScheduleDTO;
import com.welcome.bot.services.ScheduleService;

@RestController
public class ScheduleController {
	
	@Autowired
	ScheduleService scheduleService;
	
	@PostMapping("/api/schedules")
	public ScheduleContentDTO createSchedule(@RequestBody ScheduleDTO scheduleModel){
		return scheduleService.createSchedule(scheduleModel);
	}
	@GetMapping("/api/schedules")
	public Page<ScheduleContentDTO> getAllSchedules(Pageable pageable) {
		return scheduleService.getAllSchedules(pageable);
	}
	@GetMapping("/api/schedules/{scheduleId}")
	public ScheduleContentDTO getSchedule(@PathVariable Integer scheduleId) {
		return scheduleService.getSchedule(scheduleId);
	}
	@PutMapping("/api/schedules/{scheduleId}")
	public ScheduleContentDTO updateSchedule(@PathVariable Integer scheduleId, @RequestBody ScheduleDTO scheduleModel) {
		return scheduleService.updateSchedule(scheduleId, scheduleModel);
	}
	@DeleteMapping("/api/schedules/{scheduleId}")
	public ResponseEntity<Schedule> deleteSchedule(@PathVariable Integer scheduleId) {
		return scheduleService.deleteSchedule(scheduleId);
	}
}
