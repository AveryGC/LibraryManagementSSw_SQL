package com.SS.training.entity;

import java.util.List;

public class Borrower {
    private Integer cardNo;
    private String name;
    private String address;
    private String phone;
    private List<Loans> loans;

    public static void readHeader(){
        System.out.printf("%-28s||%-28s||%-15s||%-25s","Borrower Name","Address","Phone Number","Loans\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }
    public void read(){
        System.out.printf("%-30s%-30s%-17s",name,address,phone);
        loans.forEach(l-> System.out.printf("%-28s",l.getBook().getTitle()));
        System.out.println();
    }

    public List<Loans> getLoans() {
        return loans;
    }

    public void setLoans(List<Loans> loans) {
        this.loans = loans;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
