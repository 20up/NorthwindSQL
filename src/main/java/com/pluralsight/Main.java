package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = configDataSource(args);
        boolean choice = true;

        while (choice) {
            System.out.print("""
                    \nWhat do you want to do?
                      1) Display all products
                      2) Display all customers
                      3) Display all category's
                      0) Exit
                     Select an option:""");
            String enter = scanner.nextLine().trim();

            switch (enter) {
                case "1" -> AllProduct(dataSource);
                case "2" -> AllCustomer(dataSource);
                case "3" -> AllCategory(dataSource);
                case "0" -> {
                    System.out.println("\nbye \uD83D\uDE25"); choice = false;
                }
                default -> System.out.println("\ntry again no option available\n");
            }

        }

    }

    public static DataSource configDataSource(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);

        return dataSource;
    }

    public static void AllProduct(DataSource dataSource) {


        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductID, ProductName, UnitPrice, UnitsInStock From products");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                String name = resultSet.getString("ProductName");
                double price = resultSet.getDouble("UnitPrice");
                int stock = resultSet.getInt("UnitsInStock");

                System.out.printf("ID:%d | Name:%s | Price:%.2f | Stock:%d%n", id, name, price, stock);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("\nallP exception\n");
        }

    }

    public static void AllCustomer(DataSource dataSource) {

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT CustomerID, ContactName, CompanyName, City, country, Phone FROM customers");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("ID:" + rs.getString("CustomerID") + "| ContactName:" + rs.getString("ContactName") + "| CompanyName:" + rs.getString("CompanyName") + "| City:" + rs.getString("City") + "| Country:" + rs.getString("country") + "| Phone#:" + rs.getString("Phone"));
            }

        } catch (SQLException e) {
            System.out.println("\nAllC exception\n");
        }
    }

    public static void AllCategory(DataSource dataSource){
        try{
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM categories");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println("ID:"+ rs.getInt("CategoryID") + "| Name:" + rs.getString("CategoryName"));
            }

        }catch (SQLException e){
            System.out.println("bad");
        }

    }


}