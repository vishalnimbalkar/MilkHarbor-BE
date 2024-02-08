package com.milkharbor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.milkharbor.dao.AdminDao;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendEmail(String email) {
    	 SimpleMailMessage message = new SimpleMailMessage();
         message.setFrom("milkharbor78@gmail.com");
         message.setTo(email);
         message.setSubject("Invitation to Join MilkHarbor");
         message.setText("Dear User,\r\n" + 
         		"\r\n" + 
         		"Greetings from the MilkHarbor family! We are thrilled to extend a warm invitation for you to join our growing community at MilkHarbor. Your presence would add immense value to our team, and we believe your skills and expertise would contribute significantly to our shared goals.\r\n" + 
         		"\r\n" + 
         		"To get started, kindly register using the following link:\r\n" + 
         		"http://localhost:4200/farmer-signup" +
         		"\r\n" + 
         		"We look forward to welcoming you to the MilkHarbor community!\r\n" + 
         		"\r\n" + 
         		"Best Regards,\r\n" + 
         		"MilkHarbor Team\r\n" + 
         		"");
         javaMailSender.send(message);
      
         return "Success";
	}
    
}

