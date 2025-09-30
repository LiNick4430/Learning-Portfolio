package com.example.demo.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.dto.BookDTO;
import com.example.demo.service.BookService;

@SpringBootTest
public class FindBook {

	@Autowired
	BookService bookService;
	
	@Test
	void find() {
		List<BookDTO> books = bookService.findAllBooks();
		books.forEach(book -> {
			System.out.println(book.getName());
		});
	}
}
