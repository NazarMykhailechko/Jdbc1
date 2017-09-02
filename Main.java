package sql;
import java.sql.*;

public class Main {

    private static final String DB_CONN = "jdbc:mysql://localhost:3306/apartments";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "WIN72007@NAZAr";
    private static Connection conn;

    private static void createTable() {
        try(Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS APARTMENT (Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                                                                  "District VARCHAR (55), " +
                                                                  "Address VARCHAR (55), " +
                                                                  "Square DOUBLE, " +
                                                                  "Rooms INT, " +
                                                                  "Price DOUBLE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void fillTable(){
        try(Statement st = conn.createStatement()) {
            st.execute("INSERT INTO APARTMENT (District, Address, Square, Rooms, Price) VALUES ('Шевченківський р-н','Коперника 7', 38.99, 1, 50000.00)," +
                                                                                                 "('Соломянський р-н','Липківського,16А', 50.24, 1, 59000.00)," +
                                                                                                 "('Дарницький р-н','Чавдар,11', 77.78, 2, 85000.00)," +
                                                                                                 "('Шевченківський р-н','Татарська,20', 72.33, 3, 95000.00)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void SelectApartmentByDistrict(String district){
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM APARTMENT WHERE DISTRICT = ?")) {
            ps.setString(1,district);
            getTable(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void SelectApartmentBySquare(Double square){
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM APARTMENT WHERE SQUARE >= ? ORDER BY 4 DESC")) {
            ps.setDouble(1,square);
            getTable(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getTable(PreparedStatement ps){
        try(ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData rsm = rs.getMetaData();

            for (int i = 1; i < rsm.getColumnCount(); i++) {
                System.out.print(rsm.getColumnName(i) + "\t\t");

            }
            System.out.println();
            while (rs.next()) {
                for (int i = 1; i < rsm.getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + "\t\t");
                }
                System.out.println();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public static void main(String[] args) throws SQLException {

        try {
            conn = DriverManager.getConnection(DB_CONN,DB_USER,DB_PASS);
            createTable();
            fillTable();
            SelectApartmentByDistrict("Шевченківський р-н");
            SelectApartmentBySquare(50.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
        }
    }
}