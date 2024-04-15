package com.github.sbouclier.javarestbooks.repository;

import com.github.sbouclier.javarestbooks.domain.Book;
import com.github.sbouclier.javarestbooks.domain.BookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Locale;

/**
 * Book repository
 *
 * @author Stéphane Bouclier
 *
 */
public interface BookRepository extends JpaRepository<Book, BookId> {
    //Optional<Book> findByIsbn(String isbn);

    List<Book> findByLanguage(Locale language);
}
