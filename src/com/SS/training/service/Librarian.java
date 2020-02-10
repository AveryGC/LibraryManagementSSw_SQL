package com.SS.training.service;

import com.SS.training.Input.InputValidation;
import com.SS.training.dao.BranchDAO;
import com.SS.training.entity.Branch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Librarian {
    public void mainMenu(Scanner scanner){
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+                                           Librarian Menu                                               +");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        boolean cont= true;
        Connection conn =null;
        try {
            ConnectUtil util = ConnectUtil.getInstance();
            conn = util.getConnection();
            BranchDAO bdao = new BranchDAO(conn);
            List<Branch> branches = bdao.read();
            while(cont){
                System.out.println("Welcome Librarian. Please enter the Branch you manage.");
                System.out.print("   ");
                Branch.readHeader();
                branches.forEach(b->{
                    System.out.print((branches.indexOf(b)+1)+".)");
                    b.read();
                });
                System.out.println((branches.size()+1)+".)exit");
                String line = scanner.nextLine();
                if(InputValidation.checkInput(1,(branches.size()+1),line)){
                    int input = Integer.parseInt(line);
                    if(input==(branches.size()+1)){
                        System.out.println("Exiting Library Menu");
                        cont=false;
                    }
                    else{
                        branchOptions(scanner,branches.get(input-1), conn);
                        cont=false;
                    }
                }
                else {
                    System.out.println("!!!!!Improper Input Format!!!!");
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR WITH DATABASE");
        } catch (ClassNotFoundException e) {
            System.out.println("!!!!!JDBC Driver not Found!!!!!!");
        }
    }

    public void branchOptions(Scanner scanner, Branch branch, Connection conn) throws SQLException {
        boolean cont = true;
        while (cont) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("You are currently signed into branch:");
            branch.read();
            System.out.println("Enter the task you would like to do:");
            System.out.println("1.)Update Branch name\n2.)Update branch address\n3.)Exit");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,3,line)){
                int input = Integer.parseInt(line);
                if(input==1){
                    updateBranchName(scanner,branch,conn);
                }
                if(input==2){
                    updateBranchAddress(scanner,branch,conn);
                }
                if(input == 3)
                    cont=false;
            }
            else {
                System.out.println("!!!Improper Input!!!!!");
            }
        }
    }
    protected void updateBranchName(Scanner scanner, Branch branch, Connection conn) throws SQLException {
        System.out.println("Please enter the new name of the Branch.");
        String newName = scanner.nextLine();
        branch.setBranchName(newName);
        BranchDAO bdao = new BranchDAO(conn);
        try {
            bdao.update(branch);
            conn.commit();
        }
        catch (Exception e){
            System.out.println("!!!!!ERROR BRANCH NOT UPDATED!!!!!");
            conn.rollback();
        }
    }
    protected void updateBranchAddress(Scanner scanner, Branch branch, Connection conn) throws SQLException {
        System.out.println("Please enter the new name of the Address.");
        String newAddress = scanner.nextLine();
        branch.setBranchAddress(newAddress);
        BranchDAO bdao = new BranchDAO(conn);
        try {
            bdao.update(branch);
            conn.commit();
        }
        catch (Exception e){
            System.out.println("!!!!!ERROR BRANCH NOT UPDATED!!!!!");
            conn.rollback();
        }
    }
}

