package revature.brown.daotests;


import org.junit.jupiter.api.*;
import revature.brown.daos.ClientDAO;
import revature.brown.daos.ClientDaoLocal;
import revature.brown.daos.ClientDaoPostgres;
import revature.brown.entities.Client;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientDaoTests {

    private static ClientDAO clientDAO = new ClientDaoPostgres();
    private static Client testClient = null;

    @Test
    @Order(1)
    void create_client() {
        Client c1 = new Client(0, "William", "Brown");
        Client c2 = new Client(0, "John", "Deere");
        clientDAO.createClient(c1);
        clientDAO.createClient(c2);
        testClient = c1;
        Assertions.assertNotEquals(0,testClient.getClientId());
        System.out.println("CREATE_CLIENT TEST:");
        System.out.println("The following clients are stored on the DB:");
        System.out.println(testClient);
        System.out.println(c2);
    }

    @Test
    @Order(2)
    void get_client_by_id(){
        int id = testClient.getClientId();
        Client client = clientDAO.getClientById(id);

        Assertions.assertEquals(testClient.getClientFirstName(), client.getClientFirstName());
        System.out.println("GET_CLIENT_BY_ID TEST:");
        System.out.println("The client that was retrieved is "+client);
    }

    @Test
    @Order(3)
    void update_client(){
        int id = testClient.getClientId();
        Client client = clientDAO.getClientById(id);
        String newFirstName = "Hogan";
        client.setClientFirstName(newFirstName);
        clientDAO.updateClient(client);

        Client updatedClient = clientDAO.getClientById(id);
        Assertions.assertEquals(newFirstName,updatedClient.getClientFirstName());
        System.out.println("UPDATE_CLIENT TEST:");
        System.out.println("Old client: "+client);
        System.out.println("Updated client: "+updatedClient);
    }

    @Test
    @Order(4)
    void get_all_clients(){
        Client c2 = new Client(0,"Hayden","Brown");
        Client c3 = new Client(0, "Michael","McCracken");
        Client c4 = new Client(0, "Harrison", "Haas");
        Client c5 = new Client(0, "Paige", "Haas");
        clientDAO.createClient(c2);
        clientDAO.createClient(c3);
        clientDAO.createClient(c4);
        clientDAO.createClient(c5);

        Set<Client> allClients = clientDAO.getAllClients();
        Assertions.assertTrue(allClients.size()>3);
        System.out.println("GET_ALL_CLIENTS TEST:");
        System.out.println(allClients);
    }


    @Test
    @Order(5)
    void delete_client_by_id(){
        System.out.println("DELETE_CLIENT_BY_ID:");
        Set<Client> allClients = clientDAO.getAllClients();
        System.out.println("Size before deleting: "+allClients.size());

        int id = testClient.getClientId();
        boolean result = clientDAO.deleteClientById(id);

        Assertions.assertTrue(result);
        Set<Client> allClientPostDelete = clientDAO.getAllClients();
        System.out.println("Size after deleting: "+ allClientPostDelete.size());

    }

}
