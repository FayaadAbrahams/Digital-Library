package com.thoughtprocess.bookstore.model;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private Double cost;


    public Book(long id, String title, String author, Double cost) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.cost = cost;
    }


    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", cost=" + cost +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
