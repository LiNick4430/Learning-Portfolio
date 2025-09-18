package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.model.dto.BookDTO;
import com.example.demo.model.entity.Book;

public interface BookService {

	Book toBook(BookDTO bookDTO);
	BookDTO toBookDTO (Book book);
	
	BookDTO addBook(BookDTO bookDTO);
	BookDTO updateBookById(Integer id, BookDTO bookDTO)  throws BookNotFoundException;
	void deleteBookById(Integer id)  throws BookNotFoundException;
	
	List<BookDTO> findAllBooks();
	List<BookDTO> findByPage(Integer start, Integer size);
	
	BookDTO findBookById(Integer id)  throws BookNotFoundException;
}
