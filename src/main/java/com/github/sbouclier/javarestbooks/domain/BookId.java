package com.github.sbouclier.javarestbooks.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class BookId implements Serializable {

    private String isbn;

    protected BookId() {
    }

    public BookId(String isbn) {
        defineIsbn(isbn);
    }

    private void defineIsbn(final String newBook) {
        if (isbnMeetsMinimumRequirements(newBook)) {
            this.isbn = newBook;
        } else {
            throw new IllegalArgumentException("Invalid Book Id");
        }
    }

    private boolean isbnMeetsMinimumRequirements(final String newBook) {
        return !newBook.isEmpty() && newBook != null && Pattern.matches("^97[8-9]-[0-9]{10}$", newBook);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbnMeetsMinimumRequirements(isbn)) {
            this.isbn = isbn;
        } else {
            throw new IllegalArgumentException("Invalid BooK Id");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return Objects.equals(isbn, bookId.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
