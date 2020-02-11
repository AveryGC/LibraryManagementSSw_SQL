package com.SS.training.service.AdminSubMenus;

import com.SS.training.Input.InputValidation;
import com.SS.training.dao.BorrowerDAO;
import com.SS.training.entity.Borrower;
import com.SS.training.service.ConnectUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminBorrower {
    public void adminBorrowers(Scanner scanner){
        boolean cont = true;
        while (cont){
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Select what action you would like to execute:");
            System.out.println("1.)Add Borrowers\n2.)Delete Borrowers\n3.)Update Borrowers\n4.)Read All Borrowers\n5.)Exit to Admin Menu");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,5,line)){
                int input = Integer.parseInt(line);
                if(input==1){
                    adminAddBorrower(scanner);
                }else if(input==2){
                    System.out.println("delete");
                }else if(input==3){
                    adminUpdateBorrowers(scanner);
                }else if(input==4){
                    readAllBorrowers();
                }else if(input==5){
                    System.out.println("Exiting to Main");
                    cont =false;
                }
            }
        }
    }
    protected void adminUpdateBorrowers(Scanner scanner) {
        try{
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            BorrowerDAO bdao = new BorrowerDAO(conn);
            List<Borrower> borrowers = bdao.read();
            boolean cont = true;
            while(cont){
                System.out.println("Please enter the Borrower you would like to update.");
                System.out.print("|||||");
                Borrower.readHeader();
                borrowers.forEach(b->{
                    System.out.print((borrowers.indexOf(b)+1)+".)");
                    b.read();
                });
                System.out.println((borrowers.size()+1)+".)exit");
                String line = scanner.nextLine();
                if(InputValidation.checkInput(1,(borrowers.size()+1),line)){
                    int input = Integer.parseInt(line);
                    if(input==(borrowers.size()+1)){
                        System.out.println("Canceling Update");
                        cont=false;
                    }
                    else{
                        adminUpdatedBorrowerSub(scanner,borrowers.get(input-1), conn);
                        cont=false;
                    }
                }
                else {
                    System.out.println("!!!!!Improper Input Format!!!!");
                }
            }
        }catch (SQLException e){
            System.out.println("!!!Error With Database!!!!");
        }catch (ClassNotFoundException e){
            System.out.println("!!!!Error with Database Driver!!!!");
        }
    }
    protected void adminUpdatedBorrowerSub(Scanner scanner,Borrower borrower, Connection conn){
        boolean cont = true;
        while(cont){
            System.out.println("Select what you would like to update:\n1.)Update Borrower Name\n2.)Update Borrower Address\n3.)Update Phone Number\n4.)Cancel Update");
            String line = scanner.nextLine();
            if (InputValidation.checkInput(1,4,line)){
                System.out.println("Insert the replacement information:");
                String newInfo = scanner.nextLine();
                int input = Integer.parseInt(line);
                if(input==1){
                    borrower.setName(newInfo);
                    updateBorrower(borrower,conn);
                }else if(input==2){
                    borrower.setAddress(newInfo);
                    updateBorrower(borrower,conn);
                }else if(input==3){
                    borrower.setPhone(newInfo);
                    updateBorrower(borrower,conn);
                }else if(input==4){
                    System.out.println("Update Canceled");
                }
                cont =false;
            }else{
                System.out.println("!!!!!Improper Input!!!!!");
            }
        }
    }
    protected void updateBorrower(Borrower borrower, Connection conn) {
        try {
            try {
                BorrowerDAO bdao = new BorrowerDAO(conn);
                bdao.update(borrower);
                conn.commit();
            } catch (SQLException e) {
                System.out.println("!!!DATABASE ERROR!!!!");
                conn.rollback();
            }
        }catch(SQLException e){}
    }
    protected Borrower adminAddBorrower(Scanner scanner){
        System.out.println("Enter the name of the full Name of the new borrower:");
        String name = scanner.nextLine().trim();
        System.out.println("Enter the "+ name +"'s full address:");
        String address = scanner.nextLine().trim();
        System.out.println("Enter the " + "'s phone number:");
        String phoneNumber = scanner.nextLine().trim();
        Borrower newBorrower = new Borrower();
        newBorrower.setPhone(phoneNumber);
        newBorrower.setAddress(address);
        newBorrower.setName(name);
        addBorrower(newBorrower);
        return newBorrower;
    }
    protected void addBorrower(Borrower borrower){
        try {
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            try {
                BorrowerDAO bdao = new BorrowerDAO(conn);
                int id = bdao.add(borrower);
                conn.commit();
                borrower.setCardNo(id);
            } catch (SQLException e) {
                System.out.println("!!!Error With Database!!!!");
                conn.rollback();
            }
        }catch (ClassNotFoundException e) {
            System.out.println("!!!!Error with Database Driver!!!!");
        }catch (Exception e){

        }
    }
    protected void readAllBorrowers(){
        try{
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            BorrowerDAO bdao = new BorrowerDAO(conn);
            List<Borrower> borrowers = bdao.read();
            System.out.print("|||");
            Borrower.readHeader();
            borrowers.forEach(b->{
                System.out.print((borrowers.indexOf(b)+1) + ".)");
                b.read();
            });
        } catch (SQLException e){
            System.out.println("!!!Error With Database!!!!");
        }catch (ClassNotFoundException e){
            System.out.println("!!!!Error with Database Driver!!!!");
        }
    }
}
