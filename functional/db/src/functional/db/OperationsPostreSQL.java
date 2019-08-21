package functional.db;

import java.sql.*;
import java.util.Properties;

public class OperationsPostreSQL {
    public static void main(String...args) {
        String URL = "jdbc:postgresql://localhost:5432/java9";
        Properties prop = new Properties();
        prop.put("user", "chappy");
        prop.put("password", "12345678");
        try (Connection conn = DriverManager.getConnection(URL, prop)){
            try (Statement st = conn.createStatement()) {
                boolean res = st.execute("select id,type,value from \"public\".enums");
                if (res) {
                    ResultSet rs = st.getResultSet();
                    while(rs.next()) {
                        int id = rs.getInt(1);
                        String type = rs.getString(2);
                        String value = rs.getString(3);
                        System.out.println("id = "  + id + ", type = " + type + ", value = " + value);
                    }
                } else {
                    int count = st.getUpdateCount();
                    System.out.println("Update count = " + count);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
