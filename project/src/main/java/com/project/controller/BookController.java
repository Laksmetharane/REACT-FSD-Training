package com.project.controller;

import com.project.dto.BookReqDto;
import com.project.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/addBook/{id}")
    public void addBook(@Valid  @RequestBody BookReqDto bookReqDto ,@PathVariable int id){
        bookService.addBook(bookReqDto,id);
    }

}
