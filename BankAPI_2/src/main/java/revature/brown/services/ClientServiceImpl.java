package revature.brown.services;

import org.apache.log4j.Logger;
import revature.brown.daos.ClientDAO;
import revature.brown.entities.Client;

import java.util.Set;

public class ClientServiceImpl implements ClientService {

    private static Logger logger = Logger.getLogger(ClientServiceImpl.class);

    private ClientDAO cDAO;

    // DEPENDENCY INJECTION
    public ClientServiceImpl(ClientDAO clientDAO){this.cDAO = clientDAO;}

    @Override
    public Client registerClient(Client client) {

        this.cDAO.createClient(client);
        logger.info("Registered: Client "+client.getClientId());
        return client;
    }

    @Override
    public Set<Client> getAllClients() {
        logger.info("Retrieved: All Clients");
        return this.cDAO.getAllClients(); }

    @Override
    public Client getClientById(int clientId) {
        logger.info("Retrieved: Client "+clientId);
        return this.cDAO.getClientById(clientId); }

    @Override
    public Client updateClient(Client client) {

        Client oldClient = this.cDAO.getClientById(client.getClientId());

        this.cDAO.updateClient(client);
        logger.info("Updated: Client "+client.getClientId());
        return client;
    }

    @Override
    public boolean deleteClientById(int id) {
        logger.info("Deleted: Client "+id);
        return this.cDAO.deleteClientById(id); }
}
