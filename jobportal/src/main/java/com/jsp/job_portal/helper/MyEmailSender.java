package com.jsp.job_portal.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jsp.job_portal.dto.JobSeeker;
import com.jsp.job_portal.dto.Recruiter;

import jakarta.mail.internet.MimeMessage;

@Service
public class MyEmailSender {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	private static final String FROM_EMAIL = "saishkulkarni7@gmail.com"; // change if needed

	public void sendOtp(JobSeeker jobSeeker) {
		sendOtpEmail(jobSeeker.getEmail(), jobSeeker, "Otp for Creating Account with Us");
	}

	public void sendOtp(Recruiter recruiter) {
		sendOtpEmail(recruiter.getEmail(), recruiter, "Otp for Creating Account with Us");
	}

	private void sendOtpEmail(String toEmail, Object user, String subject) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(FROM_EMAIL, "Job-Portal Application");
			helper.setTo(toEmail);
			helper.setSubject(subject);

			Context context = new Context();
			context.setVariable("x", user); // "x" used in your Thymeleaf template
			String body = templateEngine.process("otp-template.html", context);

			helper.setText(body, true);

			mailSender.send(message);
			System.out.println("Email sent successfully to: " + toEmail);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to send email to: " + toEmail);
		}
	}
}
