package com.notification_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.notification_service.dto.BookingEvent;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendBookingConfirmation(BookingEvent event) throws MessagingException {
        //SimpleMailMessage message = new SimpleMailMessage();
    	MimeMessage message = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message,true);
    	helper.setTo(event.getEmail());
    	helper.setSubject("🎫 Booking Confirmation: " + event.getMovieName());
    	
    	Context context = new Context();
    	context.setVariable("userName", event.getUserId());
        context.setVariable("movieName", event.getMovieName());
        context.setVariable("bookingId", event.getBookingId());
        context.setVariable("ticketLink", "https://yourapp.com/tickets/" + event.getBookingId());
        context.setVariable("posterUrl", event.getMoviePosterUrl());
        String html = templateEngine.process("booking-confirmation", context);
        helper.setText(html, true);

        mailSender.send(message);
        System.out.println("📧 Email sent to " + event.getEmail());
    }
}
