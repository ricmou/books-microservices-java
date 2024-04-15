package com.github.sbouclier.javarestbooks.services;

import com.github.sbouclier.javarestbooks.domain.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // String to Locale
        Converter<String, Locale> stringToLocale = ctx -> ctx.getSource() == null ? null : Locale.forLanguageTag(ctx.getSource());

        //BookDescriptionDto to BookDescription
        TypeMap<BookDescriptionDto, BookDescription> typeMapBookDescStringLocale = modelMapper.createTypeMap(BookDescriptionDto.class, BookDescription.class);
        typeMapBookDescStringLocale.addMappings(mapper -> {
            mapper.using(stringToLocale).map(BookDescriptionDto::getLanguage, BookDescription::setLanguage);
        });

        //BookDto to Book
        TypeMap<BookDto, Book> typeMapBookStringLocale = modelMapper.createTypeMap(BookDto.class, Book.class);
        typeMapBookStringLocale.addMappings(mapper -> {
            mapper.using(stringToLocale).map(BookDto::getLanguage, Book::setLanguage);
        });

        //CreatingBookDto to Book
        TypeMap<CreatingBookDto, Book> typeMapCreateBookStringLocale = modelMapper.createTypeMap(CreatingBookDto.class, Book.class);
        typeMapCreateBookStringLocale.addMappings(mapper -> {
            mapper.using(stringToLocale).map(CreatingBookDto::getLanguage, Book::setLanguage);
        });

        //Locale to String
        Converter<Locale, String> LocaleToString = ctx -> ctx.getSource() == null ? null : (ctx.getSource().toLanguageTag());

        //BookDescription to BookDescriptionDto
        TypeMap<BookDescription, BookDescriptionDto> typeMapBookDescLocaleString = modelMapper.createTypeMap(BookDescription.class, BookDescriptionDto.class);
        typeMapBookDescLocaleString.addMappings(mapper -> {
            mapper.using(LocaleToString).map(BookDescription::getLanguage, BookDescriptionDto::setLanguage);
        });

        //Book to BookDto
        TypeMap<Book, BookDto> typeMapBookLocaleString = modelMapper.createTypeMap(Book.class, BookDto.class);
        typeMapBookLocaleString.addMappings(mapper -> {
            mapper.using(LocaleToString).map(Book::getLanguage, BookDto::setLanguage);
        });

        return modelMapper;
    }
}