package com.github.sbouclier.javarestbooks.controller;

import co.mouris.protofileslib.book.*;
import com.github.sbouclier.javarestbooks.domain.BookDescriptionDto;
import com.github.sbouclier.javarestbooks.domain.BookDto;
import com.github.sbouclier.javarestbooks.domain.CreatingBookDto;
import com.github.sbouclier.javarestbooks.exception.BookNotFoundException;
import com.github.sbouclier.javarestbooks.services.IBookService;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class BookControllerGrpc extends APIBooksBookGRPCGrpc.APIBooksBookGRPCImplBase {

    private final IBookService bookService;

    @Autowired
    public BookControllerGrpc(IBookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void getBookByISBN(RequestWithISBN request, StreamObserver<BookGrpcDto> responseObserver) {
        BookDto posBook = bookService.Get(request.getISBN());

        BookGrpcDto reply = BookGrpcDto.newBuilder()
                .setISBN(posBook.getIsbn())
                .setName(posBook.getTitle())
                .setLanguage(posBook.getLanguage())
                .addAllDescriptions(posBook.getDescriptions().stream()
                        .map(description -> DescriptionGrpcDto.newBuilder()
                                .setText(description.getText())
                                .setLanguage(description.getLanguage())
                                .build())
                        .toList())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllBooks(Empty request, StreamObserver<BookGrpcDto> responseObserver) {
        List<BookDto> lstBook = bookService.GetAll();

        for (BookDto posBook: lstBook)
        {
            BookGrpcDto reply = BookGrpcDto.newBuilder()
                    .setISBN(posBook.getIsbn())
                    .setName(posBook.getTitle())
                    .setLanguage(posBook.getLanguage())
                    .addAllDescriptions(posBook.getDescriptions().stream()
                            .map(description -> DescriptionGrpcDto.newBuilder()
                                    .setText(description.getText())
                                    .setLanguage(description.getLanguage())
                                    .build())
                            .toList())
                    .build();
            responseObserver.onNext(reply);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getAllBooksOfLanguage(RequestWithLanguage request, StreamObserver<BookGrpcDto> responseObserver) {
        List<BookDto> lstBook = bookService.GetAllOfLanguage(request.getLanguage());

        for (BookDto posBook: lstBook)
        {
            BookGrpcDto reply = BookGrpcDto.newBuilder()
                    .setISBN(posBook.getIsbn())
                    .setName(posBook.getTitle())
                    .setLanguage(posBook.getLanguage())
                    .addAllDescriptions(posBook.getDescriptions().stream()
                            .map(description -> DescriptionGrpcDto.newBuilder()
                                    .setText(description.getText())
                                    .setLanguage(description.getLanguage())
                                    .build())
                            .toList())
                    .build();
            responseObserver.onNext(reply);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void addNewBook(CreatingBookGrpcDto request, StreamObserver<BookGrpcDto> responseObserver) {

        BookDto posBook = bookService.Add(new CreatingBookDto(request.getISBN(), request.getName(), request.getLanguage(), List.copyOf(request.getDescriptionsList().stream()
                .map(description ->
                        new BookDescriptionDto(description.getText(), description.getLanguage())).
                toList())));

        BookGrpcDto reply = BookGrpcDto.newBuilder()
                .setISBN(posBook.getIsbn())
                .setName(posBook.getTitle())
                .setLanguage(posBook.getLanguage())
                .addAllDescriptions(posBook.getDescriptions().stream()
                        .map(description -> DescriptionGrpcDto.newBuilder()
                                .setText(description.getText())
                                .setLanguage(description.getLanguage())
                                .build())
                        .toList())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }

    @Override
    public void modifyBook(BookGrpcDto request, StreamObserver<BookGrpcDto> responseObserver) {
        BookDto posBook = bookService.Update(request.getISBN() ,new BookDto(request.getISBN(), request.getName(), request.getLanguage(),List.copyOf(request.getDescriptionsList().stream()
                .map(description ->
                        new BookDescriptionDto(description.getText(), description.getLanguage())).
                toList())));

        BookGrpcDto reply = BookGrpcDto.newBuilder()
                .setISBN(posBook.getIsbn())
                .setName(posBook.getTitle())
                .setLanguage(posBook.getLanguage())
                .addAllDescriptions(posBook.getDescriptions().stream()
                        .map(description -> DescriptionGrpcDto.newBuilder()
                                .setText(description.getText())
                                .setLanguage(description.getLanguage())
                                .build())
                        .toList())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteBook(RequestWithISBN request, StreamObserver<DeleteSuccess> responseObserver) {
        try{
            bookService.Delete(request.getISBN());
            DeleteSuccess reply = DeleteSuccess.newBuilder().setSuccess(true).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
        catch (BookNotFoundException bookNotFoundException)
        {
            DeleteSuccess reply = DeleteSuccess.newBuilder().setSuccess(false).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}