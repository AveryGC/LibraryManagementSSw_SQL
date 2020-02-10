package com.SS.training.service;


import com.SS.training.Input.InputValidation;

import java.util.Scanner;

public class Admin {
    public void adminMainMenu(Scanner scanner){
        boolean cont = true;
        while (cont){
            System.out.println("Welcome Administrator, please select what option you would like to commit.");
            System.out.println("1.)Edit/Add Authors\n2.)Edit/Add Books\n3.)Edit/Add Genres\n4.)Edit/Add Publishers\n5.)Edit/Add Library Branches\n6.)Edit/Add Borrowers\n7.)Exit to Main");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,6,line)){
                int input =Integer.parseInt(line);
                switch (input){
                    case 1:
                        System.out.println("author");
                        break;
                    case 2:
                        System.out.println("books");
                        break;
                    case 3:
                        System.out.println("genres");
                        break;
                    case 4:
                        System.out.println("publishers");
                        break;
                    case 5:
                        System.out.println("Branches");
                        break;
                    case 6:
                        System.out.println("Borrowers");
                        break;
                    case 7:
                        cont=false;
                        System.out.println("Exiting to Main");
                        break;
                }
            }else {
                System.out.println("!!!!Improper Input Format!!!!");
            }
        }
    }

    protected void adminAuthor(Scanner scanner){
        System.out.println("Select what action you would like to execute:");
        System.out.println("1.)Add Author\n2.)Delete Author\n3.)Update Author\n4.)Read All Authors";
    }
}
