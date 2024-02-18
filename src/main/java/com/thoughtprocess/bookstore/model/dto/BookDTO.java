package com.thoughtprocess.bookstore.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class BookDTO {
    private final Double cost;
    private final String title;
    private final String author;

    public BookDTO(Double cost, String title, String author) {
        this.cost = cost;
        this.title = title;
        this.author = author;
    }
}
