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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.domain.Schedule;
import com.welcome.bot.models.ScheduleDTO;
import com.welcome.bot.security.CurrentUser;
import com.welcome.bot.security.UserPrincipal;
import com.welcome.bot.models.ScheduleCreateDTO;
import com.welcome.bot.services.ScheduleService;

import net.minidev.json.JSONArray;

@RestController
public class ScheduleController {
	
	@Autowired
	ScheduleService scheduleService;
	
	@PostMapping("/api/schedules")
	public ScheduleDTO createSchedule(@RequestBody ScheduleCreateDTO scheduleModel, @CurrentUser UserPrincipal userPrincipal){
		return scheduleService.createSchedule(scheduleModel, userPrincipal);
	}
	@GetMapping("/api/schedules")
	public Page<ScheduleDTO> getAllSchedules(Pageable pageable, @CurrentUser UserPrincipal userPrincipal) {
		return scheduleService.getAllSchedules(pageable, userPrincipal);
	}
	@GetMapping("/api/schedules/{scheduleId}")
	public ScheduleDTO getSchedule(@PathVariable Integer scheduleId) {
		return scheduleService.getSchedule(scheduleId);
	}
	@PutMapping("/api/schedules/{scheduleId}")
	public ScheduleDTO updateSchedule(@PathVariable Integer scheduleId, @RequestParam(name = "active") boolean active) {
		return scheduleService.updateSchedule(scheduleId, active);
	}
	@DeleteMapping("/api/schedules/{scheduleId}")
	public ResponseEntity<Schedule> deleteSchedule(@PathVariable Integer scheduleId) {
		return scheduleService.deleteSchedule(scheduleId);
	}
	@GetMapping("/api/schedule-intervals")
	public JSONArray getRepeatIntervals() {
		return scheduleService.getRepeatIntervals();
	}
}
