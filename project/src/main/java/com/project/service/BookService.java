package com.project.service;

import com.project.dto.BookReqDto;
import com.project.mapper.BookMapper;
import com.project.model.Author;
import com.project.model.Book;
import com.project.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public void addBook(BookReqDto dto, int id) {
        Book book = BookMapper.MapDtoToEntity(dto);
        Author author = authorService.getById(id);
        book.setAuthor(author);
        bookRepository.save(book);
    }
}
