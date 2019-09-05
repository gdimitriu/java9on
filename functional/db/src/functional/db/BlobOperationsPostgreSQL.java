package functional.db;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class BlobOperationsPostgreSQL {

    public static void main(String...args) {
        createTables();
        //insertImageNotImplemented();
        //insertTextNotImplemented();
        insertImage();
        insertUsingByteImage();
        insertOID();
        insertTextReadAsString();
        insertTextReadAsStream();
        dropTables();
    }

    private static void createTables() {
        execute("create table \"public\".images (id integer, image bytea)");
        execute("create table \"public\".lobs (id integer, lob oid)");
        execute("create table \"public\".texts (id integer, text text)");
    }

    private static void dropTables() {
        execute("drop table \"public\".images");
        execute("drop table \"public\".lobs");
        execute("drop table \"public\".texts");
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

    /* not implemented in PostgreSql */
    private static void insertImageNotImplemented() {
        try (Connection conn = getDbConnection()) {
            String sql = "insert into \"public\".images (id, image) values(?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                File file = new File("image1.jpg");
                FileInputStream fis = new FileInputStream(file);
                Blob blob = conn.createBlob();
                OutputStream out = blob.setBinaryStream(1);
                int i = -1;
                while((i = fis.read()) != -1) {
                    out.write(i);
                }
                st.setBlob(2, blob);
                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertImage() {
        System.out.println("insertImage");
        traverseRS("select * from \"public\".images");
        System.out.println();
        try(Connection conn = getDbConnection()) {
            String sql = "insert into \"public\".images (id, image) values(?,?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                File file = new File("image1.jpg");
                FileInputStream fis = new FileInputStream(file);
                st.setBinaryStream(2, fis);
                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
            sql = "select image from \"public\".images where id =?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                try (ResultSet rs = st.executeQuery()) {
                    while(rs.next()) {
                        try (InputStream is = rs.getBinaryStream(1)) {
                            int i;
                            System.out.println("ints = ");
                            while((i = is.read()) != -1) {
                                System.out.print(i);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
        traverseRS("select * from \"public\".images");
    }

    private static void insertUsingByteImage() {
        System.out.println("insertUsingByteImage");
        traverseRS("select * from \"public\".images");
        System.out.println();

        try (Connection conn = getDbConnection()) {
            String sql = "insert into \"public\".images (id, image) values(?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1,200);
                File file = new File("image1.jpg");
                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = fis.readAllBytes();
                st.setBytes(2, bytes);
                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
            sql = "select image from \"public\".images where id = ?";
            System.out.println();
            try(PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 200);
                try (ResultSet rs = st.executeQuery()) {
                    while(rs.next()) {
                        byte[] bytes = rs.getBytes(1);
                        System.out.println("bytes = " + bytes);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
        traverseRS("select * from \"public\".images");
    }

    /* not implemented in PostgreSql */
    private static void insertTextNotImplemented() {
        try(Connection conn = getDbConnection()) {
            String sql = "insert into \"public\".texts (id, text) values(?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                File file = new File("src/functional/db/OperationsPostreSQL.java");
                Reader reader = new FileReader(file);
                st.setClob(2, reader);
                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertOID() {
        System.out.println("inserOID");
        traverseRS(" select * from \"public\".lobs");
        System.out.println();
        long lob = -1;
        try(Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            LargeObjectManager lobm = conn.unwrap(org.postgresql.PGConnection.class).getLargeObjectAPI();
            lob = lobm.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);
            LargeObject obj = lobm.open(lob, LargeObjectManager.WRITE);
            File file = new File("image1.jpg");
            try (FileInputStream fis = new FileInputStream(file)) {
                int size = 2048;
                byte[] bytes = new byte[size];
                int len = 0;
                while((len= fis.read(bytes, 0 , size)) > 0) {
                    obj.write(bytes, 0 , len);
                }
                obj.close();
                String sql = "insert into \"public\".lobs (id, lob) values(?,?)";
                try(PreparedStatement st = conn.prepareStatement(sql)) {
                    st.setInt(1, 100);
                    st.setLong(2, lob);
                    st.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //read the lob
        try(Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            LargeObjectManager lobm = conn.unwrap(org.postgresql.PGConnection.class).getLargeObjectAPI();
            String sql = "select lob from \"public\".lobs where id = ?";
            try(PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1,100);
                try (ResultSet rs = st.executeQuery()) {
                    while(rs.next()) {
                        long readlob = rs.getLong(1);
                        LargeObject obj = lobm.open(readlob, LargeObjectManager.READ);
                        byte[] bytes = new byte[obj.size()];
                        obj.read(bytes, 0, obj.size());
                        System.out.println("bytes = " + bytes);
                        obj.close();
                    }
                }
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //delete the blob
        if(lob != -1) {
            execute("select lo_unlink(" + lob + ")");
        }
        traverseRS(" select * from \"public\".lobs");
    }
    private static void insertTextReadAsString() {
        System.out.println("insertTextReadAsString");
        traverseRS("select * from \"public\".texts");
        System.out.println();
        try(Connection conn = getDbConnection()) {
            String sql = "insert into \"public\".texts (id, text) values(?,?)";
            try(PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                File file = new File("src/functional/db/OperationsPostreSQL.java");
                try(FileInputStream fis = new FileInputStream(file)) {
                    byte[] bytes = fis.readAllBytes();
                    st.setString(2, new String(bytes, Charset.forName("UTF-8")));
                }
                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
            sql = "select text from \"public\".texts where id = ?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                try (ResultSet rs = st.executeQuery()) {
                    while(rs.next()) {
                        String str = rs.getString(1);
                        System.out.println(str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertTextReadAsStream() {
        System.out.println("insertTextReadAsStream");
        traverseRS("select * from \"public\".texts");
        System.out.println();
        try(Connection conn = getDbConnection()) {
            String sql = "insert into \"public\".texts (id, text) values(?,?)";
            try(PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 200);
                File file = new File("src/functional/db/OperationsPostreSQL.java");
                try (FileReader reader = new FileReader(file)) {
                    //This is not implemented
                    //st.setCharacterStream(2, reader, file.length());
                    st.setCharacterStream(2, reader, (int)file.length());
                }
                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
            sql = "select text from \"public\".texts where id = ?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 200);
                try (ResultSet rs = st.executeQuery()) {
                    while(rs.next()) {
                        try (Reader reader = rs.getCharacterStream(1)) {
                            char[] chars = new char[160];
                            int read =reader.read(chars);
                            System.out.println(chars);
                            while(read > 0) {
                                for(int i = 0 ; i < 160;i ++) {
                                    chars[i]='\0';
                                }
                                read =reader.read(chars);
                                System.out.println(chars);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
