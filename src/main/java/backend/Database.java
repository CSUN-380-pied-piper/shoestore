package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final String url = "jdbc:postgresql://bgapytnwjskfjif8rjg3-postgresql.services.clever-cloud.com:5432/bgapytnwjskfjif8rjg3";
    private final String user = "udaqis555zzuvohu7keb";
    //private final String password = "paste_password_here";
    private final String password = "je1q5ubCx9wRrssgxo1V";


    private Connection conn;

    public Database() {
    }

    private void openConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the DB successfully.");
            this.conn = conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection connect() {
        if (this.conn == null) {
            this.openConnection();
        }
        return this.conn;
    }

    public Customer getCustomer(String email) {
        String sqlQuery = "SELECT * FROM customers WHERE email like '" + email + "'";
        Statement stmt;
        ResultSet results;
        List<Customer> custList = new ArrayList<>();
        try {
            // open the db connection...
            openConnection();
            // now create and run our query
            stmt = conn.createStatement();
            results = stmt.executeQuery(sqlQuery);
            // while we have results from the query, parse them...
            while (results.next()) {
                String fname = results.getString("firstname");
                String lname = results.getString("lastname");
                String street = results.getString("street");
                String unit = results.getString("unit");
                String city = results.getString("city");
                String state = results.getString("state");
                Integer zip = results.getInt("zipcode");
                String phone = results.getString("phone");
                custList.add(new Customer(fname, lname, phone, email, street, unit, city
                , state, zip));
            }
            // cleanup/close our DB connection when we're done with it.
            stmt.close();
            results.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Customer: " + custList.get(0));
        return custList.get(0);
    }

    public ArrayList<Product> getProducts(String name) {
        String sqlQuery = "SELECT * FROM products WHERE name like '" + name + "'";
        ArrayList<Product> productList = new ArrayList<>();
        Statement stmt;
        ResultSet results;
        try {
            // open the db connection...
            openConnection();
            // now create and run our query
            stmt = conn.createStatement();
            results = stmt.executeQuery(sqlQuery);
            // while we have results from the query, parse them...
            while (results.next()) {
                String shoeName = results.getString("name");
                Double shoePrice = results.getDouble("price");
                Product p = new Product(shoeName, shoePrice);
                productList.add(p);
            }
            // cleanup/close our DB connection when we're done with it.
            stmt.close();
            results.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
}
