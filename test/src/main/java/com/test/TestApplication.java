package com.test;


import org.modelmapper.ModelMapper;
import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.test.service.FilesStorageService;

@SpringBootApplication
public class TestApplication implements CommandLineRunner{
	
	@Resource
	  FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		storageService.deleteAll();
	    storageService.init();
		
	}
}