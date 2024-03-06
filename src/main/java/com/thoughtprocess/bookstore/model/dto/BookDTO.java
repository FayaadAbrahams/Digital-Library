package com.thoughtprocess.bookstore.model.dto;


import lombok.ToString;

import javax.validation.constraints.*;

@ToString
public class BookDTO {

    @NotNull(message = "Cost cannot be empty")
    @DecimalMin(value = "0.01", message= "Cost must be greater than 0" )
    @DecimalMax(value = "999.99", message= "Cost cannot be greater than 999")
    private Double cost;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    private String author;

    public BookDTO(Double cost, String title, String author) {
        this.cost = cost;
        this.title = title;
        this.author = author;
    }

    public BookDTO() {
        this.cost = 0.00;
        this.title = "";
        this.author = "";
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
