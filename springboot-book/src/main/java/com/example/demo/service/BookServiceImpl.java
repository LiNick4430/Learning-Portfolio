package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.model.dto.BookDTO;
import com.example.demo.model.entity.Book;
import com.example.demo.repository.BookDao;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BookDao bookDao;
	
	@Override
	public Book toBook(BookDTO bookDTO) {
		return modelMapper.map(bookDTO, Book.class);
	}
	@Override
	public BookDTO toBookDTO(Book book) {
		return modelMapper.map(book, BookDTO.class);
	}
	@Override
	public BookDTO addBook(BookDTO bookDTO) {
		bookDao.save(toBook(bookDTO));
		return bookDTO;
	}
	@Override
	public BookDTO updateBookById(Integer id, BookDTO bookDTO)  throws BookNotFoundException{
		findBookById(id);
		bookDTO.setId(id);
		bookDao.updateById(id, toBook(bookDTO));
		return bookDTO;
	}
	@Override
	public void deleteBookById(Integer id)  throws BookNotFoundException{
		findBookById(id);
		bookDao.deleteById(id);
	}
	@Override
	public List<BookDTO> findAllBooks() {
		return bookDao.findAll().stream().map(book -> toBookDTO(book)).toList();
	}
	@Override
	public List<BookDTO> findBooksByPage(Integer start, Integer size) {
		return bookDao.findBooksByPage(start, size).stream().map(book -> toBookDTO(book)).toList();
	}
	@Override
	public BookDTO findBookById(Integer id) throws BookNotFoundException{
		Optional<Book> optBook = bookDao.findBookById(id);
		if (optBook.isEmpty()) {
			throw new BookNotFoundException("查無書籍, id = " + id);
		}
		return toBookDTO(optBook.get());
	}
}
