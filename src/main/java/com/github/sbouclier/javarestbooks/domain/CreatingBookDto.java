package com.github.sbouclier.javarestbooks.domain;

import java.util.List;

public class CreatingBookDto {
    private String isbn;
    private String title;
    private String language;
    private List<BookDescriptionDto> descriptions;

    public CreatingBookDto() {
    }

    public CreatingBookDto(String isbn, String title, String language, List<BookDescriptionDto> descriptions) {
        this.isbn = isbn;
        this.title = title;
        this.language = language;
        this.descriptions = descriptions;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<BookDescriptionDto> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<BookDescriptionDto> descriptions) {
        this.descriptions = descriptions;
    }
}
