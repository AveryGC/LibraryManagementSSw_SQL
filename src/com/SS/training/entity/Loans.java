package com.SS.training.entity;

import java.sql.Date;

public class Loans {
    private Book book;
    private Branch branch;
    private Borrower borrower;
    private Date dueDate;
    private Date dateOut;
    private Date dateIn;

    public static void readHeader(){
        System.out.printf("%-18s||%-23s||%-18s||%-13s||%-10s\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n",
                "Borrower","Book Title","Library Branch","Date Out","Due Date");
    }

    public void read(){
        System.out.printf("%-20s%-25s%-20s%-15tD%-15tD\n",
                borrower.getName(),book.getTitle(),branch.getBranchName(),dateOut,dueDate);
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

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }
}
