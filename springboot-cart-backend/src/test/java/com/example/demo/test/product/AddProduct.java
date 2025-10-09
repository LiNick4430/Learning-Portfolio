package com.example.demo.test.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.cart.model.entity.Product;
import com.example.demo.cart.model.entity.ProductImage;
import com.example.demo.cart.repository.ProductRepository;

@SpringBootTest
public class AddProduct {

	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void addProduct() {
		
		// 建立商品圖片
		ProductImage appleImage = new ProductImage();
		String appleImageBase64 = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAANlBMVEUAAAD///+ZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmXmQE53AAAAD3RSTlMAEiAjJCQhOx8dNx4/8NrpAAAAUklEQVQYV2NgoCbgYGBgGGBoYGBiE2SJYOJk5mRgYGRgYHDoYmJhYBgxkN4c0BCQ1JTA78jFAWZmQGAjUU9PLv4+gTsMASt+BhHghphmIMADbNAFf+8spTAAAAAElFTkSuQmCC";
		appleImage.setImageBase64(appleImageBase64);
		
		ProductImage bananaImage = new ProductImage();
		String bananaImageBase64 = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAZlBMVEUAAAD///+ZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZktjCjAAAAGHRSTlMANHhCT2Z7hJGz41Ads/TFa4QQLdrMQAAAF5JREFUGNNtzkcSgjAQBdA6BScz3Pb//+jUkdmoZrh13kRggJOiLJKkNTASd6w85wOmiK2wJFvGYZf90Wsf+TUYR3kGZVVknqssamLUofbQxazv0l1T6/xPqQfWCeTfbtWIAAAAASUVORK5CYII=";
		bananaImage.setImageBase64(bananaImageBase64);
		
		// 建立商品
		Product apple = new Product();
		apple.setName("蘋果");
		apple.setPrice(100);
		apple.setProductImage(appleImage);
		
		Product banana = new Product();
		banana.setName("香蕉");
		banana.setPrice(80);
		banana.setProductImage(bananaImage);
		
		// 儲存商品
		productRepository.save(apple);
		productRepository.save(banana);
		
		System.out.println("商品 儲存 成功");
		
	}
}
