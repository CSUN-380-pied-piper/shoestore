package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final String url = "jdbc:postgresql://bgapytnwjskfjif8rjg3-postgresql.services.clever-cloud.com:5432/bgapytnwjskfjif8rjg3";
    private final String user = "udaqis555zzuvohu7keb";
    private final String password = "paste_password_here";

    private Connection conn;

    public Database() {
    }

    private void openConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
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
}
