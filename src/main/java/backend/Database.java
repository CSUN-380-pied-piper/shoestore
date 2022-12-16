package backend;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

/**
 * The type Database.
 */
public class Database {

    private final String url = "jdbc:postgresql://bgapytnwjskfjif8rjg3-postgresql.services.clever-cloud.com:5432/bgapytnwjskfjif8rjg3";
    private final String user = "udaqis555zzuvohu7keb";
    private final String password = "paste_password_here";

    private Connection conn;

    /**
     * The enum Tables.
     */
    enum TABLES {
        /**
         * Customers tables.
         */
        CUSTOMERS,
        /**
         * Products tables.
         */
        PRODUCTS,
        /**
         * Orders tables.
         */
        ORDERS,
        /**
         * Contents tables.
         */
        CONTENTS,
        /**
         * Hashes tables.
         */
        HASHES
    }

    /**
     * Instantiates a new Database.
     */
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

    /**
     * Creates a connection to our database server if none exists.
     */
    public void connect() {
        if (this.conn == null) {
            this.openConnection();
        }
    }

    private Product parseOrders(ResultSet rs) throws SQLException {
        return null;
    }

    private Product parseContents(ResultSet rs) throws SQLException {
        String shoeName = rs.getString("name");
        Double shoePrice = rs.getDouble("price");
        Product shoe = new Product(shoeName, shoePrice);
        shoe.lastSizeProp().set(rs.getDouble("size"));
        shoe.lastQtyProp().set(rs.getInt("qty"));
        return shoe;
    }

    /**
     * Parses every row returned when querying our products table
     *
     * @param rs    The ResultSet object, which we use to parse the
     *              individual row/record information
     * @return      A new Product object, with the relevant fields
     *              populated from the db cursor/record
     * @throws SQLException
     */
    private Product parseProduct(ResultSet rs) throws SQLException {
        String shoeName = rs.getString("name");
        Double shoePrice = rs.getDouble("price");
        Integer minSize = rs.getInt("minsize");
        Integer maxSize = rs.getInt("maxsize");
        boolean half = rs.getBoolean("halfsizes");
        BigInteger id = BigInteger.valueOf(rs.getInt("id"));
        return new Product(shoeName, shoePrice, half, minSize, maxSize, id);
    }

    /**
     * Parses every row returned by our customers query
     *
     * @param rs    the ResultSet object, which supplies us with the cursor
     *              location (i.e. allows us to access our row information)
     *              for a single row/record in the customers table
     * @return      new Customer object, with the necessary fields populated
     *              from our database record
     * @throws SQLException
     */
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
        BigInteger id = BigInteger.valueOf(rs.getInt("id"));
        return new Customer(fname, lname, phone, email, street, unit, city
                , state, zip, id);
    }

    private HashedPass parseHashes(ResultSet rs) throws SQLException {
        String combined = rs.getString("hash");
        return new HashedPass(combined);
    }

    private void runQuery(String sql, TABLES t, ArrayList<Queryable> l) throws SQLException {
        // open the db connection...
        connect();
        // now create and run our query
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet results = stmt.executeQuery();
        // while we have results from the query, parse them...
        while (results.next()) {
            switch(t) {
                case ORDERS: l.add(parseOrders(results)); break;
                case CONTENTS: l.add((parseContents(results))); break;
                case PRODUCTS: l.add(parseProduct(results)); break;
                case CUSTOMERS: l.add(parseCustomer(results)); break;
                case HASHES: l.add(parseHashes(results)); break;
            }
        }
        // cleanup/close our DB connection when we're done with it.
        stmt.close();
        results.close();
    }

    /**
     * Gets contents of an order from the database
     *
     * @param orderId the order id
     *
     * @return the contents, as an ArrayList, of Product objects, with lastSize
     *         and lastQty properties pre-set to the correct values
     */
    public ArrayList<Queryable> getContents(BigInteger orderId) {
        String sqlQuery = "SELECT c.size, c.qty, p.name, p.price " +
                          "FROM contents as c, products as p " +
                          "WHERE c.orderid=" + orderId + " and p.id=c.prodid";
        ArrayList<Queryable> productList = new ArrayList<>();
        try {
            runQuery(sqlQuery, TABLES.CONTENTS, productList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    /**
     * Inserts order and shopping cart information into the database
     * order information will be inserted into the 'orders' table
     * and individual cart items will be inserted into the 'contents' table
     *
     * @param cart  the shopping cart object from the successful order
     * @param email the customer email address, used to identify the customer
     *              record/account in the database
     * @throws SQLException
     */
    public void insertOrder(ShoppingCart cart, String email) throws SQLException {
        Customer c = getCustomer(email);
        ResultSet generatedKeys;
        int orderKey = 0;
        String orderSql = "INSERT INTO orders (customer, total, shipped) VALUES (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(orderSql,
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,c.getId());
        statement.setDouble(2,cart.getFinalTotal());
        statement.setBoolean(3,false);
        // insert the order record into the db now
        if (statement.executeUpdate() > 0) {
            // Retrieve our generated pkey
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderKey = generatedKeys.getInt(1);
            }
        }
        // now insert the order/cart contents into the db
        String contSql = "INSERT INTO contents (orderid, prodid, size, qty) VALUES (?,?,?,?)";
        PreparedStatement cntStmt = conn.prepareStatement(contSql, Statement.RETURN_GENERATED_KEYS);
        for (Product p : cart.getContents()) {
            cntStmt.setInt(1,orderKey);
            cntStmt.setInt(2, p.getId().intValue());
            cntStmt.setDouble(3, p.getLastSize());
            cntStmt.setInt(4, p.getLastQty());
            cntStmt.addBatch();
        }
        // now execute the batch
        cntStmt.executeBatch();
    }


    /**
     * Gets customer information from the database.
     *
     * @param email the email address of the customer we're searching for
     *
     * @return the customer object
     */
    public Customer getCustomer(String email) {
        String sqlQuery = "SELECT * FROM customers WHERE email like '" + email + "'";
        ArrayList<Queryable> custList = new ArrayList<>();
        try {
            runQuery(sqlQuery, TABLES.CUSTOMERS, custList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (Customer) custList.get(0);
    }

    public HashedPass getHash(String email) {
        String sqlQuery = "SELECT c.id, c.email, h.hash FROM " +
                "hashes as h, customers as c " +
                "WHERE c.email like '" + email + "'" +
                "and h.customer=c.id";
        ArrayList<Queryable> hashList = new ArrayList<>();
        try {
            runQuery(sqlQuery, TABLES.HASHES, hashList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (HashedPass) hashList.get(0);
    }

    /**
     * Gets product information from the database.
     *
     * @param name the name of the product we're searching for
     *
     * @return the product(s) information, as an ArrayList of Product objects.
     */
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
