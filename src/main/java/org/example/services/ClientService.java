package org.example.services;

import org.example.entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientService {

    private static final String CREATE_CLIENT_QUERY = "INSERT INTO clients (\"name\") VALUES (?)";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM clients WHERE id = ?";
    private static final String SET_NAME_QUERY = "UPDATE clients SET \"name\" = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE clients WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM clients";
    private Connection connection;
    private PreparedStatement insertClientStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement updateClientStatement;
    private PreparedStatement deleteClientStatement;
    private PreparedStatement getAllStatement;

    public ClientService(Connection connection) {
        this.connection = connection;
        try {
            this.insertClientStatement = connection.prepareStatement(CREATE_CLIENT_QUERY);
            this.getByIdStatement = connection.prepareStatement(GET_BY_ID_QUERY);
            this.updateClientStatement = connection.prepareStatement(SET_NAME_QUERY);
            this.deleteClientStatement = connection.prepareStatement(DELETE_BY_ID_QUERY);
            this.getAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }
    }

    public long create(String name) {
        try {
            this.insertClientStatement.setString(1, name);
            return this.insertClientStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
            return -1;
        }
    }

    public Optional<String> getById(long id) {
        try {
            this.getByIdStatement.setLong(1, id);
            try (ResultSet resultSet = getByIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getString("name"));
                }
            } catch (SQLException e) {
                System.out.println("Error message: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void setName(long id, String name) {
        try {
            this.updateClientStatement.setString(1, name);
            this.updateClientStatement.setLong(2, id);
            this.updateClientStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }
    }

    public void deleteById(long id) {
        try {
            this.deleteClientStatement.setLong(1, id);
            this.deleteClientStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }
    }

    public List<Client> listAll() {
        List<Client> listOfClients = new ArrayList<>();
        try (ResultSet resultSet = getAllStatement.executeQuery()) {
            while (resultSet.next()) {
                listOfClients.add(new Client(resultSet.getLong("id"), resultSet.getString("name")));
            }
            return listOfClients;
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }
        return null;
    }
}
