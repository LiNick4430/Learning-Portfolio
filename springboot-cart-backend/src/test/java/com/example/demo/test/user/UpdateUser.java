package com.example.demo.test.user;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.cart.model.entity.User;
import com.example.demo.cart.repository.UserRepository;

@SpringBootTest
public class UpdateUser {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	void changePassword() {
		
		Optional<User> optUser = userRepository.findById(1L);
		if (optUser.isEmpty()) {
			System.out.println("查無資料");
			return;
		}
		
		User user = optUser.get();
		user.setPassword("!QAZ2wsx");	// 1大2小
		
		userRepository.save(user);
		
		System.out.println("密碼更新完成");
	}
}
