package com.github.sbouclier.javarestbooks.domain;

import javax.persistence.*;
import java.util.Locale;

@Embeddable
public class BookDescription {

    private String text;


    private Locale language;

    protected BookDescription() {
    }

    public BookDescription(String text, String language) {
        defineText(text);
        defineLanguage(language);
    }

    private void defineText(final String newText) {
        if (textMeetsMinimumRequirements(newText)) {
            this.text = newText;
        } else {
            throw new IllegalArgumentException("Invalid Text");
        }
    }

    private void defineLanguage(final String newLanguage) {
        if (languageMeetsMinimumRequirements(newLanguage)) {
            this.language = Locale.forLanguageTag(newLanguage);
        } else {
            throw new IllegalArgumentException("Invalid Language");
        }
    }

    private boolean textMeetsMinimumRequirements(final String newText) {
        return (!newText.isEmpty() && newText != null && newText.length() <= 256);
    }

    private boolean languageMeetsMinimumRequirements(final String newLanguage) {
        return (!newLanguage.isEmpty() && newLanguage != null);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLanguage(Locale language) {
        this.language = language;;
    }

    public String getText() {
        return text;
    }


    public Locale getLanguage() {
        return this.language;
    }
}
