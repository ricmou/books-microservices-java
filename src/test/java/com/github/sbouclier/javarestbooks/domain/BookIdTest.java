package com.github.sbouclier.javarestbooks.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookIdTest {
    @Test
    public void testBookIdNull() {
        System.out.println("testBookIdNull");

        assertThrows(NullPointerException.class, () -> new BookId(null));
    }
    @Test
    public void testBookIdEmpty() {
        System.out.println("testBookIdEmpty");

        assertThrows(IllegalArgumentException.class, () -> new BookId(""));
    }

    @Test
    public void testBookIdInvalid() {
        System.out.println("testBookIdInvalid");

        assertThrows(IllegalArgumentException.class, () -> new BookId("AY!"));
    }
}
