package revature.brown.daos;

import revature.brown.entities.Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientDaoLocal implements ClientDAO {

    private static Map<Integer, Client> clientTable = new HashMap<Integer,Client>();
    private static int clientIdMaker = 0;

    @Override
    public Client createClient(Client client) {
        // increment client ID everytime a new client is created
        client.setClientId(++clientIdMaker);
        // emulates saving to a SQL DB by saving to a table
        clientTable.put(client.getClientId(),client);
        return client;
    }

    @Override
    public Set<Client> getAllClients() {
        // returns all client objects in client table
        Set<Client> allClients = new HashSet<Client>(clientTable.values());
        return allClients;
    }

    @Override
    public Client getClientById(int id) {
        // returns the Client object in the client table with the client ID = id
        return clientTable.get(id);
    }

    @Override
    public Client updateClient(Client client) {
        // the values in 'client' are applied to the client
        // in client table with the same id
        return clientTable.put(client.getClientId(),client);
    }

    @Override
    public boolean deleteClientById(int id) {
        Client client = clientTable.remove(id);
        if(client==null){
            return false;
        }else {
            return true;
        }
    }
}
