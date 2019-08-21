package functional.db;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestConnectionPostgreSQL {
    public static void main(String... args) {
        System.out.println("Using DriverManager");
        String URL = "jdbc:postgresql://localhost:5432/java9";
        Properties prop = new Properties();
        prop.put("user", "chappy");
        prop.put("password", "12345678");
        try {
            Connection conn = DriverManager.getConnection(URL, prop);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("Using PSSimpleDataSource");
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("localhost");
        source.setDatabaseName("java9");
        source.setLoginTimeout(10);
        source.setUser("chappy");
        source.setPassword("12345678");
        try {
            Connection conn = source.getConnection();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("Using PGConnectionPoolDataSource");
        PGConnectionPoolDataSource sourcePool = new PGConnectionPoolDataSource();
        sourcePool.setServerName("localhost");
        sourcePool.setDatabaseName("java9");
        sourcePool.setLoginTimeout(10);
        sourcePool.setUser("chappy");
        sourcePool.setPassword("12345678");
        try {
            PooledConnection conn = sourcePool.getPooledConnection();
            Connection connection = conn.getConnection();
            connection.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
