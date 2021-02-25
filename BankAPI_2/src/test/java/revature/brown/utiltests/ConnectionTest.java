package revature.brown.utiltests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import revature.brown.utils.ConnectionUtil;

import java.sql.Connection;

public class ConnectionTest {

    @Test
    void generates_connection(){
        Connection conn = ConnectionUtil.createConnection();
        System.out.println(conn);
        Assertions.assertNotNull(conn);
    }

//    @Test
//    void get_env_var(){
//        String env = System.getenv("bankpass");
//        System.out.println(env);
//    }

}
