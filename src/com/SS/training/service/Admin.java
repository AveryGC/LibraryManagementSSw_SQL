package com.SS.training.service;


import com.SS.training.Input.InputValidation;
import com.SS.training.service.AdminSubMenus.*;


import java.util.Scanner;

public class Admin {
    public void adminMainMenu(Scanner scanner){
        boolean cont = true;
        while (cont){
            System.out.println("Welcome Administrator, please select what option you would like to commit.");
            System.out.println("1.)Edit/Add Authors\n2.)Edit/Add Books\n3.)Edit/Add Genres\n4.)Edit/Add Publishers\n5.)Edit/Add Library Branches\n6.)Edit/Add Borrowers\n7.)Exit to Main");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,7,line)){
                int input =Integer.parseInt(line);
                switch (input){
                    case 1:
                        AdminAuthor author = new AdminAuthor();
                        author.adminAuthor(scanner);
                        break;
                    case 2:
                        AdminBook book = new AdminBook();
                        book.adminBook(scanner);
                        break;
                    case 3:
                        AdminGenres genre = new AdminGenres();
                        genre.adminGenre(scanner);
                        break;
                    case 4:
                        AdminPublisher publisher = new AdminPublisher();
                        publisher.adminPublishers(scanner);
                        break;
                    case 5:
                        AdminBranches branches = new AdminBranches();
                        branches.adminBranches(scanner);
                        break;
                    case 6:
                        AdminBorrower borrower = new AdminBorrower();
                        borrower.adminBorrowers(scanner);
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








}
