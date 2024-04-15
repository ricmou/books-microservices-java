package com.github.sbouclier.javarestbooks.domain;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Book entity
 *
 * @author Stéphane Bouclier
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "uk_book_isbn", columnNames = "isbn") })
public class Book {

    @Id
    private BookId isbn;

    @NotBlank
    private String title;

    @NotNull
    private Locale language;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<BookDescription> descriptions;


    // ----------------
    // - CONSTRUCTORS -
    // ----------------

    protected Book() {
        // Default constructor for Jackson
    }

    public Book(String isbn, String title, String language, List<BookDescription> descriptions) {
        this.isbn = new BookId(isbn);
        defineTitle(title);
        defineLanguage(language);
        if (descriptions != null)
            this.descriptions = descriptions;
        else
            throw new IllegalArgumentException("Invalid Descriptions");
    }

    public Book(String isbn, String title, String language){this(isbn, title, language, new ArrayList<>());}

    private void defineTitle(final String newTitle) {
        if (titleMeetsMinimumRequirements(newTitle)) {
            this.title = newTitle;
        } else {
            throw new IllegalArgumentException("Invalid Title");
        }
    }

    private void defineLanguage(final String newLanguage) {
        if (languageMeetsMinimumRequirements(newLanguage)) {
            this.language = Locale.forLanguageTag(newLanguage);
        } else {
            throw new IllegalArgumentException("Invalid Language");
        }
    }

    private boolean titleMeetsMinimumRequirements(final String newTitle) {
        return (!newTitle.isEmpty() && newTitle != null && newTitle.length() <= 64);
    }

    private boolean languageMeetsMinimumRequirements(final String newLanguage) {
        return (!newLanguage.isEmpty() && newLanguage != null);
    }

    // -------------
    // - TO STRING -
    // -------------

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("isbn", isbn)
                .append("title", title)
                .toString();
    }

    // -------------------
    // - SETTERS/GETTERS -
    // -------------------

    public void addDescription(BookDescription author) {
        this.descriptions.add(author);
    }


    public BookId getIsbn() {
        return isbn;
    }

    public void setIsbn(BookId isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BookDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<BookDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

}
