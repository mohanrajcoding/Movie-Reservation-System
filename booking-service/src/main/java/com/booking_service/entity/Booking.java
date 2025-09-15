package com.booking_service.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
	
	@Id
	private String id;
	private Long showtimeId;
	private String userId;
	private String email;

    @ElementCollection
    @CollectionTable(name = "booking_seats", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "seat_id")
    private List<String> seatIds;
    private Instant createdAt;
    private String paymentRef;
    private String status;
    private BigDecimal amount;
    private LocalDateTime showtime;
    private String movieName;
    private String moviePosterUrl;
    private String theaterName;
    private String screenName;
    
}
