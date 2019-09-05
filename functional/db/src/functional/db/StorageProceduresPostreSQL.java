package functional.db;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class StorageProceduresPostreSQL {
    public static void main(String...args) {
        replaceEx();
        invokeCreateTableTexts();
        invokeInsertText();
        invokeCountTexts();
        invokeSelectText();
        invokeSelectTextAsCursor();
    }

    private static void replaceEx() {
        try (Connection conn = getDbConnection()) {
            String sql = "{ ? = call replace(?,?,?) }";
            try (CallableStatement st = conn.prepareCall(sql)) {
                st.registerOutParameter(1, Types.VARCHAR);
                st.setString(2, "Hello, World! Hello!");
                st.setString(3,"llo");
                st.setString(4, "y");
                st.execute();
                String res = st.getString(1);
                System.out.println(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void invokeCreateTableTexts() {
        execute("create or replace function createTableTexts() " +
                " returns void as " +
                "$$ drop table if exists \"public\".texts; " +
                " create table \"public\".texts (id integer, text text); " +
                "$$ language sql");
        try(Connection conn = getDbConnection()) {
            String sql = "{ call createTableTexts() }";
            try (CallableStatement st = conn.prepareCall(sql)) {
                st.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        traverseRS("select createTableTexts()");
        traverseRS("select * from createTableTexts()");
        execute(" drop function if exists createTableTexts()");
    }

    private static void invokeInsertText() {
        execute("create or replace function insertText(int, varchar)" +
                " returns void " +
                " as $$ insert into \"public\".texts (id, text) " +
                " values($1, replace($2, 'XX', 'ext')); " +
                " $$ language sql");
        String sql = "{ call insertText(?, ?) }";
        try (Connection conn = getDbConnection()) {
            try (CallableStatement st = conn.prepareCall(sql)) {
                st.setInt(1, 1);
                st.setString(2, "TXX 1");
                st.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        execute("select insertText(2, 'TXX 2')");
        traverseRS("select * from \"public\".texts");
        execute("drop function if exists insertText()");
    }

    private static void invokeCountTexts() {
        execute("insert into \"public\".texts (id, text) values (3, 'Text 3'),(4, 'Text 4')");
        traverseRS("select * from \"public\".texts");
        execute("create or replace function countTexts() " +
                "returns bigint as " +
                "$$ select count(*) from \"public\".texts; " +
                "$$ language sql");
        String sql = "{ ? = call countTexts() }";
        try (Connection conn = getDbConnection()) {
            try(CallableStatement st = conn.prepareCall(sql)) {
                st.registerOutParameter(1, Types.BIGINT);
                st.execute();
                System.out.println("Result of countTexts()  " + st.getLong(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        traverseRS("select countTexts()");
        traverseRS("select * from countTexts()");
        execute("drop function if exists countTexts()");
    }

    //this could not be called with CallableStatement
    private static void invokeSelectText() {
        execute("create or replace function selectText(int) " +
                "returns setof texts as " +
                "$$ select * from \"public\".texts where id=$1; " +
                "$$ language sql");
        traverseRS("select selectText(1)");
        traverseRS("select * from selectText(1)");
        execute("drop function if exists selectText(int)");
    }

    private static void invokeSelectTextAsCursor() {
        execute("create or replace function selectText(int) " +
                "returns refcursor " +
                "as $$ declare curs refcursor; " +
                " begin " +
                "    open curs for select * from \"public\".texts where id=$1;" +
                "       return curs; " +
                " end; " +
                "$$ language plpgsql");
        String sql = "{ ? = call selectText(?) }";
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            try(CallableStatement st = conn.prepareCall(sql)) {
                st.registerOutParameter(1, Types.OTHER);
                st.setInt(2,2);
                st.execute();
                try (ResultSet rs = (ResultSet) st.getObject(1)) {
                    System.out.println("traverseRS(refcursor()=>rs)");
                    traverseRS(rs);
                }
            }
            conn.commit();
            traverseRS("select selectText(2)");
            traverseRS("select * from selectText(2)");
            execute("drop function if exists selectText(int)");
            execute("drop table if exists texts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection getDbConnection() throws SQLException {
        String URL = "jdbc:postgresql://localhost:5432/java9";
        Properties prop = new Properties();
        prop.put("user", "chappy");
        prop.put("password", "12345678");
        return DriverManager.getConnection(URL, prop);
    }

    private static void execute(String sql) {
        try (Connection conn = getDbConnection()) {
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void traverseRS(String sql) {
        System.out.println("traverseRS(" + sql + "):");
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                   traverseRS(rs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void traverseRS(ResultSet rs) throws Exception {
        int cCount = 0;
        Map<Integer, String> cName = new HashMap<>();
        while (rs.next()) {
            if (cCount == 0) {
                ResultSetMetaData rsmd = rs.getMetaData();
                cCount = rsmd.getColumnCount();
                for (int i = 1; i <= cCount; i++) {
                    cName.put(i, rsmd.getColumnLabel(i));
                }
            }
            List<String> l = new ArrayList<>();
            for (int i = 1; i <= cCount; i++) {
                l.add(cName.get(i) + " = " + rs.getString(i));
            }
            System.out.println(l.stream()
                    .collect(Collectors.joining(", ")));
        }
    }
}
