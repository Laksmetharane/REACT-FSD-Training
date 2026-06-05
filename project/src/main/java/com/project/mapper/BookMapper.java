package com.project.mapper;

import com.project.dto.BookReqDto;
import com.project.model.Book;

public class BookMapper {
    public static Book MapDtoToEntity(BookReqDto dto) {
        Book book = new Book();
        book.setTitle(dto.title());
        book.setSummary(dto.summary());
        return book;
    }
}
