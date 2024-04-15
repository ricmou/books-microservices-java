package com.github.sbouclier.javarestbooks.services;

import com.github.sbouclier.javarestbooks.domain.*;
import com.github.sbouclier.javarestbooks.exception.BookIsbnAlreadyExistsException;
import com.github.sbouclier.javarestbooks.exception.BookNotFoundException;
import com.github.sbouclier.javarestbooks.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class BookService implements IBookService{

    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(ModelMapper modelMapper, BookRepository bookRepository) {
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> GetAll() {
        List<Book> lstBook = bookRepository.findAll();

        if (lstBook.isEmpty()) {
            return null;
        }

        List<BookDto> lstDto = modelMapper.map(lstBook, new TypeToken<List<BookDto>>() {}.getType());

        return lstDto;
    }

    @Override
    public List<BookDto> GetAllOfLanguage(String language) {
        List<Book> lstBook = bookRepository.findByLanguage(Locale.forLanguageTag(language));

        if (lstBook.isEmpty()) {
            return null;
        }

        return modelMapper.map(lstBook, new TypeToken<List<BookDto>>() {}.getType());
    }

    @Override
    public BookDto Get(String isbn) {
        Book book = bookRepository.findById(new BookId(isbn)).orElseThrow(() -> new BookNotFoundException(isbn));

        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public BookDto Add(CreatingBookDto dto) {
        if (bookRepository.findById(new BookId(dto.getIsbn())).isPresent()) {
            throw new BookIsbnAlreadyExistsException(dto.getIsbn());
        }

        Book book = bookRepository.save(modelMapper.map(dto, Book.class));
        //return this.Get(dto.getIsbn());
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public BookDto Update(String isbn, BookDto dto) {
        Book bookToUpdate = bookRepository.findById(new BookId(isbn)).orElseThrow(() -> new BookNotFoundException(isbn));

        bookToUpdate.setTitle(dto.getTitle());
        bookToUpdate.setLanguage(Locale.forLanguageTag(dto.getLanguage()));
        bookToUpdate.setDescriptions(modelMapper.map(dto.getDescriptions(), new TypeToken<List<BookDescription>>() {
        }.getType()));
        Book bookUpdated = bookRepository.save(bookToUpdate);

        //return this.Get(isbn);
        return modelMapper.map(bookUpdated, BookDto.class);
    }

    @Override
    public void Delete(String isbn) {
        Book book = bookRepository.findById(new BookId(isbn)).orElseThrow(() -> new BookNotFoundException(isbn));
        bookRepository.delete(book);
    }
}
