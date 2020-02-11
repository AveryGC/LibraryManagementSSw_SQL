package com.SS.training.service.AdminSubMenus;

import com.SS.training.Input.InputValidation;
import com.SS.training.dao.PublisherDAO;
import com.SS.training.entity.Author;
import com.SS.training.entity.Publisher;
import com.SS.training.service.ConnectUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminPublisher {
    public void adminPublishers(Scanner scanner){
        boolean cont = true;
        while (cont){
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Select what action you would like to execute:");
            System.out.println("1.)Add Publishers\n2.)Delete Publishers\n3.)Update Publishers\n4.)Read All Publishers\n5.)Exit to Admin Menu");
            String line = scanner.nextLine();
            if(InputValidation.checkInput(1,5,line)){
                int input = Integer.parseInt(line);
                if(input==1){
                    adminAddPublisher(scanner);
                }else if(input==2){
                    adminDeletePublisher(scanner);
                }else if(input==3){
                    adminUpdatePublisher(scanner);
                }else if(input==4){
                    readAllPublishers();
                }else if(input==5){
                    System.out.println("Exiting to Main");
                    cont =false;
                }
            }
        }
    }
    protected void adminDeletePublisher(Scanner scanner) {
        try{
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            PublisherDAO pdao = new PublisherDAO(conn);
            List<Publisher> publishers = pdao.read();
            boolean cont = true;
            while(cont){
                System.out.println("Please enter the Publisher you would like to DELETE.");
                System.out.print("|||||");
                Author.readHeader();
                publishers.forEach(p->{
                    System.out.print((publishers.indexOf(p)+1)+".)");
                    p.read();
                });
                System.out.println((publishers.size()+1)+".)exit");
                String line = scanner.nextLine();
                if(InputValidation.checkInput(1,(publishers.size()+1),line)){
                    int input = Integer.parseInt(line);
                    if(input==(publishers.size()+1)){
                        System.out.println("Canceling Update");
                        cont=false;
                    }
                    else{
                        Publisher deletePub = publishers.get(input-1);
                        if(deletePub.getBooks().size()==0){
                            deletePublisher(deletePub,conn);
                        }else{
                            deletePub.getBooks().forEach(b-> System.out.println(b.getTitle()));
                            System.out.println("Are you sure, all these books will be deleted.Enter 1 for YES and 2 for NO");
                            boolean cont2 = true;
                            while(cont2){
                                line = scanner.nextLine();
                                if(InputValidation.checkInput(1,2,line)){
                                    input = Integer.parseInt(line);
                                    if(input==1)
                                        deletePublisher(deletePub,conn);
                                    if(input==2){
                                        System.out.println("Operation Cancelled");
                                    }
                                    cont2=false;
                                }
                            }
                        }
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
    protected void deletePublisher(Publisher publisher, Connection conn){
        try {
            try {
                PublisherDAO pdao = new PublisherDAO(conn);
                pdao.delete(publisher);
                conn.commit();
            } catch (SQLException e) {
                System.out.println("!!!DATABASE ERROR!!!!");
                conn.rollback();
            }
        }catch (SQLException e){}
    }
    protected void adminUpdatePublisher(Scanner scanner) {
        try{
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            PublisherDAO pdao = new PublisherDAO(conn);
            List<Publisher> publishers = pdao.read();
            boolean cont = true;
            while(cont){
                System.out.println("Please enter the Publisher you would like to update.");
                System.out.print("|||||");
                Publisher.readHeader();
                publishers.forEach(b->{
                    System.out.print((publishers.indexOf(b)+1)+".)");
                    b.read();
                });
                System.out.println((publishers.size()+1)+".)exit");
                String line = scanner.nextLine();
                if(InputValidation.checkInput(1,(publishers.size()+1),line)){
                    int input = Integer.parseInt(line);
                    if(input==(publishers.size()+1)){
                        System.out.println("Canceling Update");
                        cont=false;
                    }
                    else{
                        adminUpdatedPublisherSub(scanner,publishers.get(input-1), conn);
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
    protected void adminUpdatedPublisherSub(Scanner scanner,Publisher publisher, Connection conn){
        boolean cont = true;
        while(cont){
            System.out.println("Select what you would like to update:\n1.)Update Borrower Name\n2.)Update Borrower Address\n3.)Update Phone Number\n4.)Cancel Update");
            String line = scanner.nextLine();
            if (InputValidation.checkInput(1,4,line)){
                System.out.println("Insert the replacement information:");
                String newInfo = scanner.nextLine();
                int input = Integer.parseInt(line);
                if(input==1){
                    publisher.setPublisherName(newInfo);
                    updatePublisher(publisher,conn);
                }else if(input==2){
                    publisher.setPublisherAddress(newInfo);
                    updatePublisher(publisher,conn);
                }else if(input==3){
                    publisher.setPublisherPhone(newInfo);
                    updatePublisher(publisher,conn);
                }else if(input==4){
                    System.out.println("Update Canceled");
                }
                cont =false;
            }else{
                System.out.println("!!!!!Improper Input!!!!!");
            }
        }
    }
    protected void updatePublisher(Publisher publisher, Connection conn){
        try {
            try {
                PublisherDAO pdao = new PublisherDAO(conn);
                pdao.update(publisher);
                conn.commit();
            } catch (SQLException e) {
                System.out.println("!!!DATABASE ERROR!!!!");
                conn.rollback();
            }
        }catch (SQLException e){}
    }
    protected Publisher adminAddPublisher(Scanner scanner){
        System.out.println("Enter the name of the new Publisher:");
        String name = scanner.nextLine().trim();
        System.out.println("Enter the address of " + name +":");
        String address = scanner.nextLine().trim();
        System.out.println("Enter the phone number of " + name);
        String phoneNumber = scanner.nextLine();
        Publisher publisher = new Publisher();
        publisher.setPublisherAddress(address);
        publisher.setPublisherName(name);
        publisher.setPublisherPhone(phoneNumber);
        addPublisher(publisher);
        return publisher;
    }
    protected void addPublisher(Publisher publisher){
        try {
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            try {
                PublisherDAO pdao = new PublisherDAO(conn);
                int id = pdao.add(publisher);
                conn.commit();
                publisher.setPublisherId(id);
            } catch (SQLException e) {
                System.out.println("!!!Error With Database!!!!");
                conn.rollback();
            }
        }catch (ClassNotFoundException e) {
            System.out.println("!!!!Error with Database Driver!!!!");
        }catch (Exception e){
        }

    }
    protected void readAllPublishers(){
        try{
            ConnectUtil conUtil = ConnectUtil.getInstance();
            Connection conn = conUtil.getConnection();
            PublisherDAO pdao = new PublisherDAO(conn);
            List<Publisher> publishers = pdao.read();
            System.out.print("|||");
            Publisher.readHeader();
            publishers.forEach(p->{
                System.out.print((publishers.indexOf(p)+1) + ".)");
                p.read();
            });
        } catch (SQLException e){
            System.out.println("!!!Error With Database!!!!");
        }catch (ClassNotFoundException e){
            System.out.println("!!!!Error with Database Driver!!!!");
        }
    }
}
