package org.example;

import org.example.config.H2Db;
import org.example.entities.Client;
import org.example.services.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = H2Db.getConnection();
        ClientService clientService = new ClientService(connection);

        System.out.println("clientService.create(\"Boris\") = " + clientService.create("Boris"));
        System.out.println("clientService.getById(1) = " + clientService.getById(1));
        clientService.setName(1, "Masha");
        clientService.deleteById(1);
        List<Client> listOfClients = clientService.listAll();
        for(Client cl : listOfClients){
            System.out.println(cl);
        }
    }
}