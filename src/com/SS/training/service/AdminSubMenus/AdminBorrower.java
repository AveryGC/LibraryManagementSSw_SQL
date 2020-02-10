package com.SS.training.service.AdminSubMenus;

import com.SS.training.Input.InputValidation;
import com.SS.training.dao.BorrowerDAO;
import com.SS.training.dao.BranchDAO;
import com.SS.training.entity.Borrower;
import com.SS.training.entity.Branch;
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
                    System.out.println("update");
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
