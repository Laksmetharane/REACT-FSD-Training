package com.project.service;

import com.project.exception.ResourceNotFoundException;
import com.project.model.Author;
import com.project.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorService {
    public final AuthorRepository authorRepository;

    public Author getById(int id) {
        return authorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid author id"));
    }
}
