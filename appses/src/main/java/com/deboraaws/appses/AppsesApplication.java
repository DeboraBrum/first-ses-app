package com.deboraaws.appses;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.deboraaws.appses.services.ServiceSes;

@SpringBootApplication
public class AppsesApplication {

	public static void main(String[] args) {
		ServiceSes.sendMessage("Email da aplicacao da Deb - " + LocalDate.now());
	}

}
