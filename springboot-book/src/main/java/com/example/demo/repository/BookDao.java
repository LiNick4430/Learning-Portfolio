package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.entity.Book;

public interface BookDao {
	
	int save(Book book);
	int updateById(Integer id, Book book);
	int deleteById(Integer id) ;
	
	Optional<Book> findBookById(Integer id);
	
	List<Book> findAll();
	List<Book> findByPage(Integer start, Integer size);
}
