package com.github.sbouclier.javarestbooks.domain;

public class BookDescriptionDto {
    private String text;
    private String language;

    public BookDescriptionDto() {
    }

    public BookDescriptionDto(String text, String language) {
        this.text = text;
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
