package com.execise.estore.estore;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@SpringBootApplication
//implementing this interface to make JSON show id's in response 
@EnableAsync   //this is to allow async mail sending with userRepo.save(newUser) to have the least delay.
public class EStoreApplication implements RepositoryRestConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(EStoreApplication.class, args);
	}

	//From the implementation of RepositoryRestConfigurer:
	//@Override
   // public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        
		//config.exposeIdsFor(Governorate.class);
        
   // }
}
