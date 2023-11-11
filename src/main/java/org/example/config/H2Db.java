package org.example.config;

import org.example.props.PropertyReader;
import org.flywaydb.core.Flyway;

import java.sql.*;

public class H2Db {
    private static final H2Db INSTANCE = new H2Db();
    private static Connection connection;
    private H2Db() {
        String databaseURL = PropertyReader.getConnectionUrlH2();

        try{
            connection = DriverManager.getConnection(databaseURL);
            flywayMigration(databaseURL);
        } catch (SQLException e){
            e.fillInStackTrace();
        }
    }

    public static H2Db getInstance(){
        return INSTANCE;
    }

    public static Connection getConnection() {
        return connection;
    }

    public int executeUpdate(String query){
        try(Statement statement = connection.createStatement()){
            return statement.executeUpdate(query);
        }catch (SQLException e){
            throw new RuntimeException("Error while running query");
        }
    }

    public void processResultSet(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int durationMonths = resultSet.getInt("duration_months");
                System.out.println(id + "\n" + durationMonths);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while processing ResultSet", e);
        }
    }

    public ResultSet executeResult(String query){
        try(Statement statement = connection.createStatement()){
            return statement.executeQuery(query);
        } catch (SQLException e){
            throw new RuntimeException("Error while running query");
        }
    }

    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e){
            throw new RuntimeException("Something went wrong!");
        }
    }

    private void flywayMigration(String url){
        Flyway flyway = Flyway.configure().dataSource(url, "", "").load();
        flyway.migrate();
    }
}
