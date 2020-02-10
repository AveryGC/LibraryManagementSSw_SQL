package com.SS.training.entity;

import java.util.List;

public class Book {
    private Integer bookId;
    private String title;
    private Publisher publisher;
    private List<Author> authors;
    private List<Genre> genres;
    private List<Loans> loans;
    private List<Copies> copies;

    public static void readHeader(){
        System.out.printf("%-28s||%-23s||%-38s||%-40s \n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n",
                "Book Title","Publisher","Author","Genre");
    }

    public void read(){
        System.out.printf("%-30s%-25s",title,publisher.getPublisherName());
        authors.forEach(a->{System.out.printf("%-20s",a.getAuthorName());});
        if(authors.size()==1)
            System.out.printf("%-20s"," ");
        genres.forEach(g->System.out.printf("%-20s",g.getGenreName()));
        System.out.println();
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Loans> getLoans() {
        return loans;
    }

    public void setLoans(List<Loans> loans) {
        this.loans = loans;
    }

    public List<Copies> getCopies() {
        return copies;
    }

    public void setCopies(List<Copies> copies) {
        this.copies = copies;
    }

}
