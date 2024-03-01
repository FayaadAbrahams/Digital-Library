package com.thoughtprocess.bookstore.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@ToString
public class BookDTO {
    private Double cost;
    private String title;
    private  String author;

    public BookDTO(Double cost, String title, String author) {
        this.cost = cost;
        this.title = title;
        this.author = author;
    }

    public Double getCost() {
        return cost;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
