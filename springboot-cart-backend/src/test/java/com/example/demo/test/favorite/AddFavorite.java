package com.example.demo.test.favorite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.cart.model.entity.Product;
import com.example.demo.cart.model.entity.User;
import com.example.demo.cart.repository.ProductRepository;
import com.example.demo.cart.repository.UserRepository;

@SpringBootTest
public class AddFavorite {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void add() {
		
		// 找到 User
		User john = userRepository.findById(1L).get();
		User mary2 = userRepository.findById(5L).get();
		
		// 找到 Product
		Product apple = productRepository.findById(1L).get();
		Product banana = productRepository.findById(2L).get();
		
		// 加入關注
		john.getFavoriteProducts().add(apple);
		john.getFavoriteProducts().add(banana);
		mary2.getFavoriteProducts().add(apple);
		
		// 保存
		userRepository.save(john);
		userRepository.save(mary2);
		
		System.out.println("保存完成");
		
	}
}
