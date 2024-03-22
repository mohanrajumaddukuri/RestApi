package com.myproject.springboot.firstrestapi.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDetailsRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		repository.save(new UserDetails("Mohan","Java"));
		repository.save(new UserDetails("Pandu","Java"));
		repository.save(new UserDetails("Surya","Email"));
		repository.save(new UserDetails("Bharath","Python"));
		
		List<UserDetails> user=repository.findByRole("Java");
		for(UserDetails userdetails:user) {
			System.out.println(userdetails.getName());
		}
	}

}
