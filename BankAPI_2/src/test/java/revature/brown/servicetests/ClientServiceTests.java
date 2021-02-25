package revature.brown.servicetests;


import org.junit.jupiter.api.*;
import revature.brown.daos.ClientDaoLocal;
import revature.brown.daos.ClientDaoPostgres;
import revature.brown.entities.Client;
import revature.brown.services.ClientService;
import revature.brown.services.ClientServiceImpl;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientServiceTests {

    private static ClientService clientService = new ClientServiceImpl(new ClientDaoPostgres());
    private static Client testClient = null;

    @Test
    @Order(1)
    void register_client(){
        Client client = new Client(0, "Hogan","Brown");
        clientService.registerClient(client);
        System.out.println(client);

        Assertions.assertNotEquals(0,client.getClientId());
        testClient = client;
        System.out.println("REGISTER_CLIENT TEST:");
        System.out.println("This client has been registered:");
        System.out.println(testClient);
    }

    @Test
    @Order(2)
    void get_all_clients(){
        Client c2 = new Client(0,"Hayden","Brown");
        Client c3 = new Client(0, "Michael", "McCracken");
        Client c4 = new Client(0, "Harrison", "Haas");
        Client c5 = new Client(0, "Paige" , "Haas");
        clientService.registerClient(c2);
        clientService.registerClient(c3);
        clientService.registerClient(c4);
        clientService.registerClient(c5);

        Set<Client> allClients = clientService.getAllClients();
        Assertions.assertTrue(allClients.size()>3);
        System.out.println("GET_ALL_CLIENTS TEST:");
        System.out.println("There are "+allClients.size()+" clients in the DB...");
    }

    @Test
    @Order(3)
    void get_client_by_id(){
        int id = testClient.getClientId();
        Client client = clientService.getClientById(id);
        Assertions.assertEquals(id,client.getClientId());
        System.out.println("GET_CLIENT_BY_ID TEST:");
        System.out.println("ID: "+id);
        System.out.println("client ID: "+client.getClientId());
        System.out.println(client);


    }

    @Test
    @Order(4)
    void update_client(){
        System.out.println("UPDATE_CLIENT TEST:");
        Client client = clientService.getClientById(testClient.getClientId());
        System.out.println("Old Client First Name: " + client.getClientFirstName());
        client.setClientFirstName("William");
        clientService.updateClient(client);

        Client updatedClient = clientService.getClientById(testClient.getClientId());
        Assertions.assertEquals("William", updatedClient.getClientFirstName());
        System.out.println("New Client First Name: " + updatedClient.getClientFirstName());
    }

    @Test
    @Order(5)
    void delete_client(){
        System.out.println("DELETE_CLIENT TEST:");
        int id = testClient.getClientId();
        boolean result = clientService.deleteClientById(id);
        Assertions.assertTrue(result);
        System.out.println("Client with ID "+id+" was successfully deleted...");

    }

}
