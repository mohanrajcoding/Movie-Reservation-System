package com.booking_service.service;

import java.util.Map;

import com.booking_service.dto.CreateHoldRequestDTO;
import com.booking_service.dto.CreateHoldResponseDTO;

public interface SeatHoldService {

	String holdKey(String holdId);
	CreateHoldResponseDTO createHold(String userId, CreateHoldRequestDTO req);
	Map<String, Object> getHold(String holdId);
	void cancelHold(String userId, String holdId);
	
}
