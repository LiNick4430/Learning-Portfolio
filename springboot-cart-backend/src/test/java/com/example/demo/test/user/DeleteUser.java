package com.example.demo.test.user;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.cart.model.entity.User;
import com.example.demo.cart.repository.UserRepository;

@SpringBootTest
public class DeleteUser {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void deleteUser() {
		
		Optional<User> optUser = userRepository.findById(2L);
		if (optUser.isEmpty()) {
			System.out.println("不存在 或 已經被刪除");
			return;
		}
		
		userRepository.deleteById(2L);
		System.out.println("刪除成功");
		
	}
}
