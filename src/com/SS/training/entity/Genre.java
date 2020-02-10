package com.SS.training.entity;

import java.util.List;

public class Genre {
    private Integer genreID;
    private String genreName;
    private List<Book> books;

    public static void readHeader(){
        System.out.printf("%-23s||%-30s\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n",
                "Genre Name","Books In Genre");

    }

    public void read(){
        System.out.printf("%-25s",genreName);
        books.forEach(b-> System.out.printf("%-30s",b.getTitle()));
        System.out.println("");
    }

    public Integer getGenreID() {
        return genreID;
    }

    public void setGenreID(Integer genreID) {
        this.genreID = genreID;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
