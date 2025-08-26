package com.booking_service.service;

import java.util.UUID;

import org.springframework.stereotype.Component;
@Component
public class IdGenerator {

	public String newId(String prefix) {
        return prefix + "_" + UUID.randomUUID().toString().replace("-", "");
    }
}
