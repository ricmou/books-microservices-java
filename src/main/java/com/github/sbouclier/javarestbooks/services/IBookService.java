package com.github.sbouclier.javarestbooks.services;

import com.github.sbouclier.javarestbooks.domain.BookDto;
import com.github.sbouclier.javarestbooks.domain.CreatingBookDto;

import java.util.List;

public interface IBookService {
    List<BookDto> GetAll();

    List<BookDto> GetAllOfLanguage(String language);

    BookDto Get(String isbn);

    BookDto Add(CreatingBookDto dto);

    BookDto Update(String isbn, BookDto dto);

    void Delete(String isbn);
}
