package com.github.sbouclier.javarestbooks.controller;

import com.github.sbouclier.javarestbooks.domain.BookDto;
import com.github.sbouclier.javarestbooks.domain.CreatingBookDto;
import com.github.sbouclier.javarestbooks.exception.BookIsbnAlreadyExistsException;
import com.github.sbouclier.javarestbooks.exception.BookNotFoundException;
import com.github.sbouclier.javarestbooks.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;


/**
 * Book controller
 *
 * @author Stéphane Bouclier
 *
 */
@RestController
@RequestMapping(value = "/api/books")
public class BookController {
    private final IBookService bookService;

    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody CreatingBookDto book, UriComponentsBuilder ucBuilder) {
        try {
            return new ResponseEntity<>(bookService.Add(book), HttpStatus.OK);
        }
        catch (BookIsbnAlreadyExistsException ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        try{
            return new ResponseEntity<>(bookService.Get(isbn), HttpStatus.OK);
        }
        catch (BookNotFoundException ex){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        try {
            List<BookDto> lstBook = bookService.GetAll();

            if (lstBook == null) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(lstBook, HttpStatus.OK);
        }
        catch (BookNotFoundException ex)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }


        /*List<Book> lstBook = (List<Book>) bookRepository.findAll();

        if (lstBook.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(lstBook, HttpStatus.OK);*/
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<BookDto>> getAllBooks(@PathVariable("language") String language) {
        try {
            List<BookDto> lstBook = bookService.GetAllOfLanguage(language);

            if (lstBook == null) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(lstBook, HttpStatus.OK);

        }
        catch (BookNotFoundException ex)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("isbn") String isbn, @Valid @RequestBody BookDto book) {

        try {
            return new ResponseEntity<>(bookService.Update(isbn, book), HttpStatus.OK);
        }
        catch (BookNotFoundException ex)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable("isbn") String isbn) {
        try {
            bookService.Delete(isbn);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BookNotFoundException bookNotFoundException) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        /*return bookRepository.findByIsbn(isbn)
                .map(book -> {
                    bookRepository.delete(book);
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                })
                .orElseThrow(() -> new BookNotFoundException(isbn));*/

    }
}
