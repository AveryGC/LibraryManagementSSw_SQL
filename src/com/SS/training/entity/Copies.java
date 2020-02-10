package com.SS.training.entity;

import java.util.List;

public class Copies {
    private Book book;
    private Branch branch;
    private Integer noOfCopies;

    public static void readHeader(){
        System.out.printf("%-28s||%-58s||%-30s\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n","Book Title","Authors' Names","Number of Copies");
    }
    public void read(){
        System.out.printf("%-30s",book.getTitle());
        book.getAuthors().forEach(a-> System.out.printf("%-20s",a.getAuthorName()));
        if(book.getAuthors().size()==0)
            System.out.printf("%-60s"," ");
        else if(book.getAuthors().size()==1)
            System.out.printf("%-40s"," ");
        else if(book.getAuthors().size()==2)
            System.out.printf("%-20s"," ");
        System.out.printf("%-5d\n",noOfCopies);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Integer getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }
}
