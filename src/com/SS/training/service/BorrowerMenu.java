package com.SS.training.service;

import com.SS.training.Input.InputValidation;
import com.SS.training.dao.BorrowerDAO;
import com.SS.training.dao.BranchDAO;
import com.SS.training.dao.CopiesDOA;
import com.SS.training.dao.LoanDAO;
import com.SS.training.entity.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BorrowerMenu {
    public void MainMenu(Scanner scanner){
        System.out.println("Welcome to the SmoothStack Library System. Please enter your Card Number.");
        try {
            ConnectUtil util = ConnectUtil.getInstance();
            Connection conn = util.getConnection();
            BorrowerDAO bdao = new BorrowerDAO(conn);
            String line = scanner.nextLine();
            int cardNo = Integer.parseInt(line);
            Borrower user = bdao.readByCardNo(cardNo);
            if(user==null){
                System.out.println("Card Number doesn't exist");
            }
            else{
                boolean cont = true;
                BranchDAO brdao = new BranchDAO(conn);
                List<Branch> branches = brdao.read();
                while(cont){
                    System.out.println(user.getName() +" please Enter the Branch you are currently at");
                    System.out.print("   ");
                    Branch.readHeader();
                    branches.forEach(b->{
                        System.out.print((branches.indexOf(b)+1)+".)");
                        b.read();
                    });
                    System.out.println((branches.size()+1)+".)exit");
                    line = scanner.nextLine();
                    if(InputValidation.checkInput(1,(branches.size()+1),line)){
                        int input = Integer.parseInt(line);
                        if(input==(branches.size()+1)){
                            System.out.println("Exiting Library Menu");
                        }
                        else{
                            System.out.println("branch "+ branches.get(input-1).getBranchName() + " chosen");
                            borrowerOptions(user,branches.get(input-1),scanner,conn);
                        }
                        cont=false;
                    }
                    else {
                        System.out.println("!!!!!Improper Input Format!!!!");
                    }
                }
            }
        }catch (SQLException e) {
            System.out.println("ERROR WITH DATABASE");
        } catch (ClassNotFoundException e) {
            System.out.println("!!!!!JDBC Driver not Found!!!!!!");
        }catch (NumberFormatException e){
            System.out.println("!!!!Improper Input Format!!!!");
        }catch(IndexOutOfBoundsException e){
            System.out.println("!!!!Card Number Does Not Exist!!!!");
        }

    }

    protected void borrowerOptions(Borrower user,Branch branch,Scanner scanner, Connection conn) throws SQLException {
        boolean cont= true;
        while(cont){
            System.out.println(user.getName() + "please select the action you would like to do.");
            System.out.println("1.)Check Out Book\n2.)View Currently Owned Books\n3.)Return a Book" +
                    "\n4.)Quit to Previous Menu");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,4,line)){
                int input = Integer.parseInt(line);
                if (input==1){
                    checkOutBookMenu(user, branch, scanner, conn);
                }else if (input==2){
                    printAllLoans(user);
                }else if(input==3){
                    returnBookMenu(user, branch, scanner, conn);
                }else if(input==4){
                    System.out.println("Exiting to Main Menu");
                    cont = false;
                }
            }
            else{
                System.out.println("!!!!Improper Input!!!!!");
            }
        }
    }
    protected void returnBookMenu(Borrower user, Branch branch, Scanner scanner, Connection conn) throws SQLException {
        boolean cont = true;
        while(cont) {
            System.out.println("Select the book number you would like to return.");
            System.out.print("|||");
            Loans.readHeader();
            user.getLoans().forEach(l -> {
                System.out.print((user.getLoans().indexOf(l) + 1) + ".)");
                l.read();
            });
            System.out.println((user.getLoans().size() + 1) + ".)Cancel and Exit");
            String line = scanner.nextLine();
            if (InputValidation.checkInput(1, user.getLoans().size()+1, line)) {
                int input = Integer.parseInt(line)-1;
                if(input== user.getLoans().size()){
                    System.out.println("Return Canceled");
                    cont = false;
                }
                else{
                    Loans returnThis = user.getLoans().get(input);
                    returnBook(branch,returnThis,conn);
                    user.getLoans().remove(input);
                    cont = false;
                }
            } else {
                System.out.println("!!!!Improper Input!!!!");
            }
        }
    }
    protected void returnBook(Branch branch,Loans loan, Connection conn) throws SQLException {
        try {
            //update loan with todays date
            loan.setDateIn(Date.valueOf(LocalDate.now()));
            //update loan table
            LoanDAO ldao = new LoanDAO(conn);
            CopiesDOA cdao = new CopiesDOA(conn);
            ldao.update(loan);
            //make new copy of branch search for branch
            Copies addedCopy = null;
            for(Copies c: branch.getCopies()){
                if(loan.getBook().getBookId()==c.getBook().getBookId())
                    addedCopy= c;
            }
            if(addedCopy==null){
                addedCopy = new Copies();
                addedCopy.setBranch(branch);
                addedCopy.setBook(loan.getBook());
                addedCopy.setNoOfCopies(1);
                branch.getCopies().add(addedCopy);
                cdao.add(addedCopy);
            }else{
                addedCopy.setNoOfCopies(addedCopy.getNoOfCopies()+1);
                cdao.update(addedCopy);
            }
            //update copies table
            //commit
            conn.commit();
        }
        catch (SQLException e){
            //set loan dateIn as null
            loan.setDateIn(null);
            //rollback
            conn.rollback();
        }
    }

    protected void printAllLoans(Borrower user){
        Loans.readHeader();
        user.getLoans().forEach(l->l.read());
        System.out.println();
    }
    protected void checkOutBookMenu(Borrower user, Branch branch, Scanner scanner, Connection conn) throws SQLException {
        boolean cont = true;
        while(cont){
            System.out.print("|||");
            Copies.readHeader();
            branch.getCopies().forEach(f->{
                System.out.print((branch.getCopies().indexOf(f)+1)+".)");
                f.read();});
            System.out.println((branch.getCopies().size()+1)+".)To cancel and exit");
            System.out.println("Select the book number you would like to checkout.");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,(branch.getCopies().size()+1),line)){
                int input = Integer.parseInt(line);
                if((branch.getCopies().size()+1)==input) {
                    cont = false;
                    System.out.println("Canceling Checkout");
                }
                else{
                    Copies selectedBook = branch.getCopies().get(input - 1);
                    Loans loan = checkOutBook(user, branch, selectedBook, conn);
                    Loans.readHeader();
                    loan.read();
                    cont = false;
                }
            }
            else
                System.out.println("!!!!Improper Input!!!!");

        }
    }

    protected Loans checkOutBook(Borrower user, Branch branch, Copies selectedBook, Connection conn) throws SQLException {
        Loans newLoan = new Loans();
        newLoan.setBook(selectedBook.getBook());
        newLoan.setBorrower(user);
        newLoan.setBranch(branch);
        newLoan.setDateOut(Date.valueOf(LocalDate.now()));
        newLoan.setDueDate(Date.valueOf(LocalDate.now().plusDays(16)));
        newLoan.setDateIn(null);

        try {
            LoanDAO ldao = new LoanDAO(conn);
            CopiesDOA cdao = new CopiesDOA(conn);
            ldao.add(newLoan);
            selectedBook.setNoOfCopies(selectedBook.getNoOfCopies()-1);
            cdao.update(selectedBook);
            conn.commit();
            user.getLoans().add(newLoan);
        }
        catch (Exception e){
            System.out.println("ERROR WITH DATABASE");
            conn.rollback();
        }
        return newLoan;
    }
}
