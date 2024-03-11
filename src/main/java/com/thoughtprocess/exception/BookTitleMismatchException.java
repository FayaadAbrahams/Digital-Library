package com.thoughtprocess.exception;

public class BookTitleMismatchException extends RuntimeException {
    public BookTitleMismatchException() {
        super();
    }

    public BookTitleMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BookTitleMismatchException(final String message) {
        super(message);
    }

    public BookTitleMismatchException(final Throwable cause) {
        super(cause);
    }
}
