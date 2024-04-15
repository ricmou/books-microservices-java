package com.github.sbouclier.javarestbooks.domain;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Book test
 *
 * @author Stéphane Bouclier
 *
 */
public class BookTest {

    @Test
    public void testBookNullBookId() {
        System.out.println("testBookNullBookId");

        assertThrows(NullPointerException.class, () -> new Book(null, "Title", "pt-PT"));
    }
    @Test
    public void testBookEmptyBookId() {
        System.out.println("testBookEmptyBookId");

        assertThrows(IllegalArgumentException.class, () -> new Book("", "Title", "pt-PT"));
    }

    @Test
    public void testBookInvalidBookId() {
        System.out.println("testBookInvalidBookId");

        assertThrows(IllegalArgumentException.class, () -> new Book("AY!", "Title", "pt-PT"));
    }

    @Test
    public void testBookNullTitle() {
        System.out.println("testBookNullTitle");

        assertThrows(NullPointerException.class, () -> new Book("978-0123456789", null, "pt-PT"));
    }
    @Test
    public void testBookEmptyTitle() {
        System.out.println("testBookEmptyTitle");

        assertThrows(IllegalArgumentException.class, () -> new Book("978-0123456789", "", "pt-PT"));
    }

    @Test
    public void testBookInvalidTitle() {
        System.out.println("testBookInvalidTitle");

        assertThrows(IllegalArgumentException.class, () -> new Book("978-0123456789", RandomStringUtils.randomAlphabetic(65), "pt-PT"));
    }

    @Test
    public void testBookNullLanguage() {
        System.out.println("testBookNullLanguage");

        assertThrows(NullPointerException.class, () -> new Book("978-0123456789", "Title", null));
    }
    @Test
    public void testBookEmptyLanguage() {
        System.out.println("testBookEmptyLanguage");

        assertThrows(IllegalArgumentException.class, () -> new Book("978-0123456789", "Title", ""));
    }

    @Test
    public void testBookNullBookDescriptions() {
        System.out.println("testBookNullBookDescriptions");

        assertThrows(IllegalArgumentException.class, () -> new Book("978-0123456789", "Title", "pt-PT", null));
    }
}
