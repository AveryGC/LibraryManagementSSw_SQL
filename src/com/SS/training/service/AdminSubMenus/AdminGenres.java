package com.SS.training.service.AdminSubMenus;

import com.SS.training.Input.InputValidation;
import com.SS.training.dao.GenreDAO;
import com.SS.training.entity.Genre;
import com.SS.training.service.ConnectUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminGenres {
    public void adminGenre(Scanner scanner){
        boolean cont = true;
        while (cont){
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Select what action you would like to execute:");
            System.out.println("1.)Add Genre\n2.)Delete Genre\n3.)Update Genre\n4.)Read All Genres\n5.)Exit to Admin Menu");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,5,line)){
                int input = Integer.parseInt(line);
                if(input==1){
                    adminAddGenre(scanner);
                }else if(input==2){
                    System.out.println("delete");
                }else if(input==3){
                    System.out.println("update");
                }else if(input==4){
                    readAllGenres();
                }else if(input==5){
                    System.out.println("Exiting to Main");
                    cont =false;
                }
            }
        }
    }
    protected Genre adminAddGenre(Scanner scanner){
        System.out.println("What is the name of the Genre you would like to add:");
        Genre newGenre = new Genre();
        newGenre.setGenreName(scanner.nextLine().trim());
        addGenre(newGenre);
        return newGenre;
    }
    protected void addGenre(Genre genre){
        try {
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            try {
                GenreDAO gdao = new GenreDAO(conn);
                int id = gdao.add(genre);
                conn.commit();
                genre.setGenreID(id);
            } catch (SQLException e) {
                System.out.println("!!!Error With Database!!!!");
                conn.rollback();
            }
        }catch (ClassNotFoundException e) {
            System.out.println("!!!!Error with Database Driver!!!!");
        }catch (Exception e){
        }
    }
    protected void readAllGenres(){
        try{
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            GenreDAO gdao = new GenreDAO(conn);
            List<Genre> genres = gdao.read();
            System.out.print("|||");
            Genre.readHeader();
            genres.forEach(g->{
                System.out.print((genres.indexOf(g)+1) + ".)");
                g.read();
            });
        } catch (SQLException e){
            System.out.println("!!!Error With Database!!!!");
        }catch (ClassNotFoundException e){
            System.out.println("!!!!Error with Database Driver!!!!");
        }
    }
}
