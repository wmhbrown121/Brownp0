package revature.brown.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection(){
        //"jdbc:postgresql://104.197.147.254:5432/bankapiDB?user=bankapi&password=p0pa55"
        // COME BACK TO THIS - SETUP ENV VARS
//        String details = System.getenv("jdbc:postgresql://104.197.147.254:5432/bankapiDB?user=bankapiuser&password=p0pa55");//"jdbc:postgresql://104.197.147.254:5432/bankapiDB?user=bankapiuser&password=p0pa55");
        String details = "P0 CONN DETAILS";

        try {
            // This is a checked exception - NEED try-catch block
            //                                  VVVV
            Connection conn = DriverManager.getConnection(details);
            // This is a factory (method). Reads string, creates DB implementation...
            // Again, You pass in string details for any type of database anywhere,
            // and the DriverManager Factory will give you back a
            // connection implementation specifically for PostgreSQL
            return conn;
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

}
