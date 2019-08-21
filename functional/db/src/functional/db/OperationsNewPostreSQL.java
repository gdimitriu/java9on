package functional.db;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class OperationsNewPostreSQL {
    public static void main(String...args) {

        String query = "select id , type, value from \"public\".enums";
        executeQuery(query);
        query = "insert into \"public\".enums (id,type,value) values(8,'vechicle', 'car')";
        executeUpdate(query);
        query = "select id , type, value from \"public\".enums";
        executeQuery(query);
        query = "update \"public\".enums set value = 'bus' where value='car'";
        executeUpdate(query);
        query = "delete from \"public\".enums where value ='bus'";
        executeUpdate(query);
        query = "select id , type, value from \"public\".enums";
        executeQuery(query);
        traverseRS(query);
        prepareStetement();
        transactionOkTransactionWithError();
        transactionsOneTable();
        transactionsTwoTables();
    }
    private static int executeUpdate(String sql) {
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                int count = st.executeUpdate(sql);
                System.out.println(sql);
                System.out.println("Update count = " + count);
                return count;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    private static void executeQuery(String sql) {
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                if (rs != null) {
                    while(rs.next()) {
                        int id = rs.getInt(1);
                        String type = rs.getString(2);
                        String value = rs.getString(3);
                        System.out.println("id = "  + id + ", type = " + type + ", value = " + value);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static Connection getDbConnection() throws SQLException {
        String URL = "jdbc:postgresql://localhost:5432/java9";
        Properties prop = new Properties();
        prop.put("user", "chappy");
        prop.put("password", "12345678");
        return DriverManager.getConnection(URL, prop);
    }

    private static void traverseRS(String sql) {
        System.out.println("traverseRS(" + sql + "):");
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    int cCount = 0;
                    Map<Integer, String> cName = new HashMap<>();
                    while(rs.next()) {
                        if (cCount == 0) {
                            ResultSetMetaData rsmd = rs.getMetaData();
                            cCount = rsmd.getColumnCount();
                            for (int i = 1; i <= cCount ; i++) {
                                cName.put(i, rsmd.getColumnLabel(i));
                            }
                        }

                        List<String> l = new ArrayList<>();
                        for(int i = 1; i<= cCount; i++) {
                            l.add(cName.get(i) + " = " + rs.getString(i));
                        }
                        System.out.println(l.stream().collect(Collectors.joining(", ")));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void prepareStetement() {
        traverseRS("select * from \"public\".enums");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            String[][] values = {{"1", "vechicle", "car"}, {"2","vehicle", "truck"}};
            String sql = "insert into \"public\".enums (id,type,value) values(?,?,?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                for(String[] v : values) {
                    st.setInt(1, Integer.parseInt(v[0]));
                    st.setString(2,v[1]);
                    st.setString(3, v[2]);
                    int count = st.executeUpdate();
                    System.out.println("Update count = " + count);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println();
        traverseRS("select * from \"public\".enums");
    }

    private static void transactionOkTransactionWithError() {
        traverseRS("select * from \"public\".enums");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            String sql = "insert into \"public\".enums (id,type,value) values(10,'vehicle','car')";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                System.out.println(sql);
                System.out.println("Update count = " + st.executeUpdate());
            }
            conn.commit();
            sql = "insert into \"public\".enums (id, type value) values(20,'vechicle','truck')";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                System.out.println(sql);
                System.out.println("Update count = " + st.executeUpdate());
            }
            conn.commit();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        traverseRS("select * from \"public\".enums");
    }

    private static void transactionsOneTable() {
        traverseRS("select * from \"public\".enums");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            String[][] values = {{"1", "vehicle", "car"}, {"b", "vehicle", "truck"}, {"3", "vechicle", "crewcab"}};
            String sql = "insert into \"public\".enums (id, type, value) values(?,?,?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                for (String[] value : values) {
                    System.out.println("id=" + value[0] + ":");
                    st.setInt(1, Integer.parseInt(value[0]));
                    st.setString(2, value[1]);
                    st.setString(3, value[2]);
                    int count = st.executeUpdate();
                    conn.commit();
                    System.out.println("Update count = " + count);
                }
            } catch (Exception ex) {
                conn.rollback();
                System.out.println("rollback : " + ex.getMessage());
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        traverseRS("select * from \"public\".enums");
    }

    private static void transactionsTwoTables() {
        traverseRS("select * from \"public\".enums");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            String[][] values = {{"1", "vehicle", "car"}, {"b", "vehicle", "truck"}, {"3", "vechicle", "crewcab"}};
            String sql = "insert into \"public\".enums (id, type, value) values(?,?,?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                for (String[] value : values) {
                    try (Statement stm = conn.createStatement()) {
                        System.out.println("id=" + value[0] + ":");
                        stm.execute("insert into \"public\".test values('"+value[2] +"')");
                        st.setInt(1, Integer.parseInt(value[0]));
                        st.setString(2, value[1]);
                        st.setString(3, value[2]);
                        int count = st.executeUpdate();
                        conn.commit();
                        System.out.println("Update count = " + count);
                    } catch (Exception ex) {
                        conn.rollback();
                        System.out.println("rollback intern : " +ex.getMessage());
                    }
                }
            } catch (Exception ex) {
                conn.rollback();
                System.out.println("rollback : " + ex.getMessage());
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        traverseRS("select * from \"public\".enums");
        traverseRS("select * from \"public\".test");
    }
}
