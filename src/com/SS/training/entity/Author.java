package com.SS.training.entity;

import java.util.List;

public class Author {
    private Integer authorID;
    private String authorName;
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Integer authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public static void readHeader(){
        System.out.printf("%-23s||%-32s \n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n",
                "Author Name","Books by Author");
    }

    public void read(){
        System.out.printf("%-25s",authorName);
        books.forEach(b-> System.out.printf("%-32s",b.getTitle()));
        System.out.println();
    }
}
