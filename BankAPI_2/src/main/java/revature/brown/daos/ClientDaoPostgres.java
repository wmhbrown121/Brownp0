package revature.brown.daos;

import com.sun.jdi.ClassNotPreparedException;
import revature.brown.entities.Client;
import revature.brown.utils.ConnectionUtil;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientDaoPostgres implements ClientDAO {

    @Override
    public Client createClient(Client client) {

        try(Connection conn = ConnectionUtil.createConnection()){

            String sql = "insert into client (client_first_name,client_last_name) values (?,?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,client.getClientFirstName());
            ps.setString(2,client.getClientLastName());

            ps.execute();

            // rs is a cursor that starts before the actual first element
            ResultSet rs = ps.getGeneratedKeys(); // returns the values of generated keys
            rs.next(); // 1st element
            int key = rs.getInt("client_id");
            client.setClientId(key);

            return client;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();

            return null;
        }
    }

    @Override
    public Set<Client> getAllClients() {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from client";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Set<Client> allClients = new HashSet<Client>();

            while(rs.next()) {
                Client client = new Client();
                client.setClientId(rs.getInt("client_id"));
                client.setClientFirstName(rs.getString("client_first_name"));
                client.setClientLastName(rs.getString("client_last_name"));
                allClients.add(client);
            }

            return allClients;

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Client getClientById(int id) {

        try(Connection conn = ConnectionUtil.createConnection()){
            // Create a SQL statement to execute with a (?) parameter
            String sql = "select * from client where client_id = ?";
            // Prepare the statement to be passed through connection in PreparedStatement object (ps)
            PreparedStatement ps = conn.prepareStatement(sql);
            // Set the value of the parameter at the first (and only) index
            ps.setInt(1,id);
            // Execute the query and store the result in ResultSet object (rs)
            ResultSet rs = ps.executeQuery();
            // move the cursor to the first actual record in the results
            rs.next();

            // Instantiate a new client object
            Client client = new Client();
            client.setClientId(rs.getInt("client_id"));
            client.setClientFirstName(rs.getString("client_first_name"));
            client.setClientLastName(rs.getString("client_last_name"));

            return client;

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }

    }

    @Override
    public Client updateClient(Client client) {

        try (Connection conn = ConnectionUtil.createConnection()){

            String sql = "update client set client_first_name = ?, client_last_name = ? where client_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, client.getClientFirstName());
            ps.setString(2,client.getClientLastName());
            ps.setInt(3,client.getClientId());
            ps.executeUpdate();

            return client;

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteClientById(int id) {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            return true;

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            return false;
        }
    }
}
