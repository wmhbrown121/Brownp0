package revature.brown.daos;

import revature.brown.entities.Client;

import java.util.Set;

public interface ClientDAO {

    //CREATE
    Client createClient(Client client);

    // READ
    Set<Client> getAllClients();
    Client getClientById(int id);

    // UPDATE
    Client updateClient(Client client);

    boolean deleteClientById(int id);

}
