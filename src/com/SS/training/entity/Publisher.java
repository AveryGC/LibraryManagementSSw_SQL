package com.SS.training.entity;

import java.util.List;

public class Publisher {
    private Integer publisherId;
    private String publisherPhone;
    private String publisherName;
    private String publisherAddress;
    private List<Book> books;

    public static void readHeader(){
        System.out.printf("%-23s||%-25s||%-18s||%-25s \n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n",
                "Publisher Name","Address","Phone Number","Books by Publisher");
    }

    public void read(){
        System.out.printf("%-25s%-27s%-20s",publisherName,publisherAddress,publisherPhone);
        books.forEach(b-> System.out.printf("%-32s",b.getTitle()));
        System.out.println();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherAddress() {
        return publisherAddress;
    }

    public void setPublisherAddress(String publisherAddress) {
        this.publisherAddress = publisherAddress;
    }
}
