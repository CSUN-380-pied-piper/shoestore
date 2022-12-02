package backend;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private final String url = "jdbc:postgresql://bgapytnwjskfjif8rjg3-postgresql.services.clever-cloud.com:5432/bgapytnwjskfjif8rjg3";
    private final String user = "udaqis555zzuvohu7keb";
    private final String password = "paste_password_here";

    private Connection conn;

    enum TABLES {
        CUSTOMERS, PRODUCTS, ORDERS, CONTENTS
    }

    public Database() {
    }

    private void openConnection() {
        try {
            this.conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the DB successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void connect() {
        if (this.conn == null) {
            this.openConnection();
        }
    }

    private Product parseProduct(ResultSet rs) throws SQLException {
        String shoeName = rs.getString("name");
        Double shoePrice = rs.getDouble("price");
        Integer minSize = rs.getInt("minsize");
        Integer maxSize = rs.getInt("maxsize");
        boolean half = rs.getBoolean("halfsizes");
        return new Product(shoeName, shoePrice, half, minSize, maxSize);
    }

    private Customer parseCustomer(ResultSet rs) throws SQLException {
        String fname = rs.getString("firstname");
        String lname = rs.getString("lastname");
        String street = rs.getString("street");
        String unit = rs.getString("unit");
        String city = rs.getString("city");
        String state = rs.getString("state");
        Integer zip = rs.getInt("zipcode");
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        return new Customer(fname, lname, phone, email, street, unit, city
                , state, zip);
    }

    private void runQuery(String sql, TABLES t, ArrayList<Queryable> l) throws SQLException {
        // open the db connection...
        connect();
        // now create and run our query
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        // while we have results from the query, parse them...
        while (results.next()) {
            switch(t) {
                case ORDERS: ; break;
                case CONTENTS: ; break;
                case PRODUCTS: l.add(parseProduct(results)); break;
                case CUSTOMERS: l.add(parseCustomer(results)); break;
            }
        }
        // cleanup/close our DB connection when we're done with it.
        stmt.close();
        results.close();
    }

    public ArrayList<Queryable> getContents(String email, BigInteger orderId) {
        String sqlQuery = "SELECT * FROM contents WHERE orderid like '" + orderId + "' and";
        ArrayList<Queryable> productList = new ArrayList<>();
        try {
            runQuery(sqlQuery, TABLES.PRODUCTS, productList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }


    public Customer getCustomer(String email) {
        String sqlQuery = "SELECT * FROM customers WHERE email like '" + email + "'";
        ArrayList<Queryable> custList = new ArrayList<>();
        try {
            runQuery(sqlQuery, TABLES.CUSTOMERS, custList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Customer: " + custList.get(0));
        return (Customer) custList.get(0);
    }

    public ArrayList<Queryable> getProducts(String name) {
        String sqlQuery = "SELECT * FROM products WHERE name like '" + name + "'";
        ArrayList<Queryable> productList = new ArrayList<>();
        try {
            runQuery(sqlQuery, TABLES.PRODUCTS, productList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
}
