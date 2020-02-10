package com.SS.training;

import com.SS.training.Input.ConsoleInputUtil;
import com.SS.training.Input.InputValidation;
import com.SS.training.service.Admin;
import com.SS.training.service.BorrowerMenu;
import com.SS.training.service.ConnectUtil;
import com.SS.training.service.Librarian;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        //Program header
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+                                 SmoothStack File Management System                                     +");
        System.out.println("+                                       Coded by: Avery Corbett                                          +");
        System.out.println("+                                              Main Menu                                                 +");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        boolean cont = true;
        ConsoleInputUtil inputUtil = ConsoleInputUtil.getInstance();
        Scanner scanner = inputUtil.getScanner();
        while(cont) {
            System.out.println("Welcome to the SmoothStack Library Management System. Which type of user are you ?");
            System.out.println("1.)Librarian\n2.)Administrator\n3.)Borrower\n4.)Close Program");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1, 4, line.trim())) {
                int input = Integer.parseInt(line.trim());
                switch (input){
                    case 1:
                        System.out.println("Librarian Menu");
                        Librarian librarian = new Librarian();
                        librarian.mainMenu(scanner);
                        break;
                    case 2 :
                        Admin admin = new Admin();
                        admin.adminMainMenu(scanner);
                        break;
                    case 3:
                        BorrowerMenu borrower = new BorrowerMenu();
                        borrower.MainMenu(scanner);
                        break;
                    case 4 :
                        System.out.println("Exiting");
                        cont=false;
                        break;
                }
            }
            else{ System.out.println("!!!!!IMPROPER INPUT!!!!!!");}
        }
        try {
            ConnectUtil.deConsruct();
        }catch(Exception e){}
    }

}
