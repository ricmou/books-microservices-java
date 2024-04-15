package com.github.sbouclier.javarestbooks.services;

import com.github.sbouclier.javarestbooks.domain.*;
import com.github.sbouclier.javarestbooks.exception.BookIsbnAlreadyExistsException;
import com.github.sbouclier.javarestbooks.exception.BookNotFoundException;
import com.github.sbouclier.javarestbooks.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceTest {
    private static BookService bookService;

    @BeforeAll
    public static void setUpClass() {
        BookDescription bookDesc = new BookDescription("It's a book", "en-GB");
        List<BookDescription> lstBookDesc = new ArrayList<BookDescription>();
        lstBookDesc.add(bookDesc);
        Book book1 = new Book("978-0123456789", "Book Title", "es-MX", lstBookDesc);
        Book book2 = new Book("978-8888888888", "Another Book Title", "pt-PT", lstBookDesc);
        Book book3 = new Book("978-0123456789", "Not Book Title", "es-ES", lstBookDesc);

        List<Book> lstBook = new ArrayList<Book>();
        lstBook.add(book1);

        BookRepository bookRepo = mock(BookRepository.class);
        when(bookRepo.findAll()).thenReturn(lstBook);
        when(bookRepo.findByLanguage(Locale.forLanguageTag("es-MX"))).thenReturn(lstBook);
        when(bookRepo.findByLanguage(Locale.forLanguageTag("en-GB"))).thenReturn(new ArrayList<>());
        when(bookRepo.findById(new BookId("978-0123456789"))).thenReturn(Optional.of(book1));
        when(bookRepo.findById(new BookId("978-9999999999"))).thenThrow(new BookNotFoundException("978-9999999999"));
        when(bookRepo.findById(new BookId("978-8888888888"))).thenReturn(Optional.empty());
        when(bookRepo.save(eq(book2))).thenReturn(book2);
        when(bookRepo.save(eq(book3))).thenReturn(book3);

        bookService = new BookService(new ModelMapperConfig().modelMapper(), bookRepo);
    }

    @Test
    public void testGetAll()
    {
        System.out.println("testGetAll");
        BookDescriptionDto bookDescriptionDto = new BookDescriptionDto("It's a book", "en-GB");
        List<BookDescriptionDto> lstBookDesc = new ArrayList<BookDescriptionDto>();
        lstBookDesc.add(bookDescriptionDto);

        BookDto bookDto = new BookDto("978-0123456789", "Book Title", "es-MX", lstBookDesc);
        List<BookDto> response = bookService.GetAll();

        assertEquals(bookDto.getIsbn(), response.get(0).getIsbn());
        assertEquals(bookDto.getTitle(), response.get(0).getTitle());
        assertEquals(bookDto.getLanguage(), response.get(0).getLanguage());
        assertEquals(bookDto.getDescriptions().get(0).getText(), response.get(0).getDescriptions().get(0).getText());
        assertEquals(bookDto.getDescriptions().get(0).getLanguage(), response.get(0).getDescriptions().get(0).getLanguage());
    }

    @Test
    public void testGetByCountry()
    {
        System.out.println("testGetByCountry");
        BookDescriptionDto bookDescriptionDto = new BookDescriptionDto("It's a book", "en-GB");
        List<BookDescriptionDto> lstBookDesc = new ArrayList<BookDescriptionDto>();
        lstBookDesc.add(bookDescriptionDto);

        BookDto bookDto = new BookDto("978-0123456789", "Book Title", "es-MX", lstBookDesc);
        List<BookDto> response = bookService.GetAllOfLanguage("es-MX");

        assertEquals(bookDto.getIsbn(), response.get(0).getIsbn());
        assertEquals(bookDto.getTitle(), response.get(0).getTitle());
        assertEquals(bookDto.getLanguage(), response.get(0).getLanguage());
        assertEquals(bookDto.getDescriptions().get(0).getText(), response.get(0).getDescriptions().get(0).getText());
        assertEquals(bookDto.getDescriptions().get(0).getLanguage(), response.get(0).getDescriptions().get(0).getLanguage());
    }

    @Test
    public void testGetByCountryNull()
    {
        System.out.println("testGetByCountryNull");
        assertNull(bookService.GetAllOfLanguage("en-GB"));
    }

    @Test
    public void testGet()
    {
        System.out.println("testGet");
        BookDescriptionDto bookDescriptionDto = new BookDescriptionDto("It's a book", "en-GB");
        List<BookDescriptionDto> lstBookDesc = new ArrayList<BookDescriptionDto>();
        lstBookDesc.add(bookDescriptionDto);

        BookDto bookDto = new BookDto("978-0123456789", "Book Title", "es-MX", lstBookDesc);
        BookDto response = bookService.Get("978-0123456789");

        assertEquals(bookDto.getIsbn(), response.getIsbn());
        assertEquals(bookDto.getTitle(), response.getTitle());
        assertEquals(bookDto.getLanguage(), response.getLanguage());
        assertEquals(bookDto.getDescriptions().get(0).getText(), response.getDescriptions().get(0).getText());
        assertEquals(bookDto.getDescriptions().get(0).getLanguage(), response.getDescriptions().get(0).getLanguage());
    }

    @Test
    public void testGetException()
    {
        System.out.println("testGetException");
        assertThrows(BookNotFoundException.class, () -> bookService.Get("978-9999999999"));
    }

    @Test
    public void testAddException()
    {
        System.out.println("testAddException");
        BookDescriptionDto bookDescriptionDto = new BookDescriptionDto("It's a book", "en-GB");
        List<BookDescriptionDto> lstBookDesc = new ArrayList<BookDescriptionDto>();
        lstBookDesc.add(bookDescriptionDto);

        CreatingBookDto creatingBookDto = new CreatingBookDto("978-0123456789", "Book Title", "es-MX", lstBookDesc);
        assertThrows(BookIsbnAlreadyExistsException.class, () -> bookService.Add(creatingBookDto));
    }

    @Test
    public void testAdd()
    {
        System.out.println("testAdd");
        BookDescriptionDto bookDescriptionDto = new BookDescriptionDto("It's a book", "en-GB");
        List<BookDescriptionDto> lstBookDesc = new ArrayList<BookDescriptionDto>();
        lstBookDesc.add(bookDescriptionDto);

        CreatingBookDto creatingBookDto = new CreatingBookDto("978-8888888888", "Another Book Title", "pt-PT", lstBookDesc);
        BookDto bookDto = new BookDto("978-8888888888", "Another Book Title", "pt-PT", lstBookDesc);

        BookDto response = bookService.Add(creatingBookDto);

        assertEquals(bookDto.getIsbn(), response.getIsbn());
        assertEquals(bookDto.getTitle(), response.getTitle());
        assertEquals(bookDto.getLanguage(), response.getLanguage());
        assertEquals(bookDto.getDescriptions().get(0).getText(), response.getDescriptions().get(0).getText());
        assertEquals(bookDto.getDescriptions().get(0).getLanguage(), response.getDescriptions().get(0).getLanguage());

    }

    @Test
    public void testUpdateException()
    {
        System.out.println("testUpdateException");
        BookDescriptionDto bookDescriptionDto = new BookDescriptionDto("It's a book", "en-GB");
        List<BookDescriptionDto> lstBookDesc = new ArrayList<BookDescriptionDto>();
        lstBookDesc.add(bookDescriptionDto);

        BookDto bookDto = new BookDto("978-9999999999", "Book Title", "es-MX", lstBookDesc);
        assertThrows(BookNotFoundException.class, () -> bookService.Update("978-9999999999", bookDto));
    }

    @Test
    public void testUpdate() {
        System.out.println("testUpdate");
        BookDescriptionDto bookDescriptionDto = new BookDescriptionDto("It's a book", "en-GB");
        List<BookDescriptionDto> lstBookDesc = new ArrayList<BookDescriptionDto>();
        lstBookDesc.add(bookDescriptionDto);

        BookDto bookDto = new BookDto("978-0123456789", "Not Book Title", "es-ES", lstBookDesc);

        BookDto response = bookService.Update("978-0123456789", bookDto);

        assertEquals(bookDto.getIsbn(), response.getIsbn());
        assertEquals(bookDto.getTitle(), response.getTitle());
        assertEquals(bookDto.getLanguage(), response.getLanguage());
        assertEquals(bookDto.getDescriptions().get(0).getText(), response.getDescriptions().get(0).getText());
        assertEquals(bookDto.getDescriptions().get(0).getLanguage(), response.getDescriptions().get(0).getLanguage());
    }

    @Test
    public void testDeleteException()
    {
        System.out.println("testDeleteException");
        assertThrows(BookNotFoundException.class, () -> bookService.Delete("978-9999999999"));
    }
}
