package com.SS.training.service.AdminSubMenus;

import com.SS.training.Input.InputValidation;
import com.SS.training.dao.BookDAO;
import com.SS.training.entity.Book;
import com.SS.training.service.ConnectUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminBook {
    public void adminBook(Scanner scanner){
        boolean cont = true;
        while (cont){
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Select what action you would like to execute:");
            System.out.println("1.)Add Book\n2.)Delete Book\n3.)Update Book\n4.)Read All Books\n5.)Exit to Admin Menu");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,5,line)){
                int input = Integer.parseInt(line);
                if(input==1){
                    System.out.println("add");
                }else if(input==2){
                    System.out.println("delete");
                }else if(input==3){
                    System.out.println("update");
                }else if(input==4){
                    readAllBooks();
                }else if(input==5){
                    System.out.println("Exiting to Main");
                    cont =false;
                }
            }
        }
    }

    protected void readAllBooks(){
        try{
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            BookDAO bdao = new BookDAO(conn);
            List<Book> books = bdao.read();
            System.out.print("|||");
            Book.readHeader();
            books.forEach(b->{
                System.out.print((books.indexOf(b)+1) + ".)");
                b.read();
            });
        } catch (SQLException e){
            System.out.println("!!!Error With Database!!!!");
        }catch (ClassNotFoundException e){
            System.out.println("!!!!Error with Database Driver!!!!");
        }
    }
}
