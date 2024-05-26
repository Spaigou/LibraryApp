package for_bd;
import java.sql.*;
import java.sql.Statement;

public class DBConnection {
    private static Connection connection;
    private static Statement statement;

    private DBConnection(){}

    public static Connection get_connection(){
        return connection;
    }
    public static Statement get_statement(){
        return statement;
    }

    private static void create_statement() {
        try{
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch(SQLException exc){
            throw new RuntimeException(exc);
        }
    }

    public static void connect(){
        try{
            connection = DriverManager.getConnection(Properties.get_property(Properties.DB_URL), Properties.get_property(Properties.DB_LOGIN), Properties.get_property(Properties.DB_PASSWORD));
            create_statement();
        } catch (SQLException | RuntimeException exc) {
            throw new RuntimeException(exc);
        }
    }

}
