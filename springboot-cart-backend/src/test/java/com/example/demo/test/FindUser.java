package com.example.demo.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.cart.model.entity.User;
import com.example.demo.cart.repository.UserRepository;

@SpringBootTest
public class FindUser {

	@Autowired
	UserRepository userRepository;
	
	@Test
	void find() {
		List<User> users = userRepository.findAll();
		System.out.printf("資料筆數: %d%n", users.size());
		users.forEach(user -> {
			System.out.printf("%d\t%s\t%s%n", user.getId(), user.getUsername(), user.getPassword());
		});
	}
}
