package com.SS.training.service.AdminSubMenus;

import com.SS.training.Input.InputValidation;
import com.SS.training.dao.AuthorDAO;
import com.SS.training.entity.Author;
import com.SS.training.service.ConnectUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminAuthor {
    public void adminAuthor(Scanner scanner){
        boolean cont = true;
        while (cont){
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Select what action you would like to execute:");
            System.out.println("1.)Add Author\n2.)Delete Author\n3.)Update Author\n4.)Read All Authors\n5.)Exit to Admin Menu");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,5,line)){
                int input = Integer.parseInt(line);
                if(input==1){
                    adminAddAuthor(scanner);
                }else if(input==2){
                    System.out.println("delete");
                }else if(input==3){
                    adminUpdateAuthor(scanner);
                }else if(input==4){
                    readAllAuthors();
                }else if(input==5){
                    System.out.println("Exiting to Main");
                    cont =false;
                }
            }
        }
    }
    protected void adminUpdateAuthor(Scanner scanner) {
        try{
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            AuthorDAO adao = new AuthorDAO(conn);
            List<Author> authors = adao.read();
            boolean cont = true;
            while(cont){
                System.out.println("Please enter the Author you would like to update.");
                System.out.print("|||||");
                Author.readHeader();
                authors.forEach(a->{
                    System.out.print((authors.indexOf(a)+1)+".)");
                    a.read();
                });
                System.out.println((authors.size()+1)+".)exit");
                String line = scanner.nextLine();
                if(InputValidation.checkInput(1,(authors.size()+1),line)){
                    int input = Integer.parseInt(line);
                    if(input==(authors.size()+1)){
                        System.out.println("Canceling Update");
                        cont=false;
                    }
                    else{
                        System.out.println("Enter the new name of the author you are updating:");
                        String newName = scanner.nextLine();
                        updateAuthor(authors.get(input-1), newName ,conn);
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
    protected void updateAuthor(Author author, String newName, Connection conn){
        author.setAuthorName(newName);
        try {
            try {
                AuthorDAO adao = new AuthorDAO(conn);
                adao.update(author);
                conn.commit();
            } catch (SQLException e) {
                System.out.println("!!!DATABASE ERROR!!!!");
                conn.rollback();
            }
        }catch (SQLException e){}
    }
    protected Author adminAddAuthor(Scanner scanner){
        System.out.println("Enter the name of the author you would like to add:");
        Author newAuthor = new Author();
        newAuthor.setAuthorName(scanner.nextLine().trim());
        addAuthor(newAuthor);
        return newAuthor;
    }
    protected void addAuthor(Author author){
        try {
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            try {
                AuthorDAO adao = new AuthorDAO(conn);
                int id = adao.add(author);
                conn.commit();
                author.setAuthorID(id);
            } catch (SQLException e) {
                System.out.println("!!!Error With Database!!!!");
                conn.rollback();
            }
        }catch (ClassNotFoundException e) {
            System.out.println("!!!!Error with Database Driver!!!!");
        }catch (Exception e){
        }
    }
    protected void readAllAuthors() {
        try {
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            AuthorDAO adao = new AuthorDAO(conn);
            List<Author> authors = adao.read();
            System.out.print("||||");
            Author.readHeader();
            authors.forEach(a-> {System.out.print((authors.indexOf(a)+1) +  ".)");a.read();});
        }
        catch (SQLException e){
            System.out.println("!!!Error With Database!!!!");
        }catch (ClassNotFoundException e){
            System.out.println("!!!!Error with Database Driver!!!!");
        }
    }
}
