package com.SS.training.service.AdminSubMenus;

import com.SS.training.Input.InputValidation;
import com.SS.training.dao.PublisherDAO;
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
                    System.out.println("delete");
                }else if(input==3){
                    System.out.println("update");
                }else if(input==4){
                    readAllPublishers();
                }else if(input==5){
                    System.out.println("Exiting to Main");
                    cont =false;
                }
            }
        }
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
