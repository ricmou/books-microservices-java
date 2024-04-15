package com.github.sbouclier.javarestbooks.domain;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookDescriptionTest {
    @Test
    public void testBookDescriptionNullName() {
        System.out.println("testBookDescriptionNullName");

        assertThrows(NullPointerException.class, () -> new BookDescription(null, "CA"));
    }
    @Test
    public void testBookDescriptionEmptyName() {
        System.out.println("testBookDescriptionEmptyName");

        assertThrows(IllegalArgumentException.class, () -> new BookDescription("", "CA"));
    }

    @Test
    public void testBookDescriptionInvalidName() {
        System.out.println("testBookDescriptionInvalidName");

        assertThrows(IllegalArgumentException.class, () -> new BookDescription(RandomStringUtils.randomAlphabetic(257), "CA"));
    }

    @Test
    public void testBookDescriptionNullCountry() {
        System.out.println("testBookDescriptionNullCountry");

        assertThrows(NullPointerException.class, () -> new BookDescription("Description", null));
    }
    @Test
    public void testBookDescriptionEmptyCountry() {
        System.out.println("testBookDescriptionNullCountry");

        assertThrows(IllegalArgumentException.class, () -> new BookDescription("Description", ""));
    }

}
