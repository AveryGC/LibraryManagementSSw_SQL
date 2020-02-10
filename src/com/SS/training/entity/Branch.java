package com.SS.training.entity;

import java.util.List;

public class Branch {
    private Integer branchId;
    private String branchName;
    private String branchAddress;
    private List<Loans> loans;
    private List<Copies> copies;

    public static void readHeader(){
        System.out.printf("%-28s||%-30s \n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n",
                "Branch Name","Branch Address");
    }

    public void read(){
        System.out.printf("%-30s%-30s",branchName,branchAddress);
        System.out.println();
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
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
