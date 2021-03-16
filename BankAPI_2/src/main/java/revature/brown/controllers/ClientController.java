package revature.brown.controllers;

import com.google.gson.Gson;
import io.javalin.http.Handler;
import revature.brown.daos.ClientDaoLocal;
import revature.brown.daos.ClientDaoPostgres;
import revature.brown.entities.Client;
import revature.brown.services.ClientService;
import revature.brown.services.ClientServiceImpl;

import java.util.Set;

public class ClientController {

    private static Logger logger = Logger.getLogger(ClientController.class);

    private ClientService clientservice = new ClientServiceImpl(new ClientDaoPostgres());
    Gson gson = new Gson();

    public Handler getAllClientsHandler = (ctx) -> {

        Set<Client> allClients = this.clientservice.getAllClients();
        String clientsJSON = gson.toJson(allClients);
        ctx.result(clientsJSON);
        ctx.status(200);
    };

    public Handler createClientHandler = (ctx) -> {
        String body = ctx.body();
        Client client = gson.fromJson(body, Client.class);
        this.clientservice.registerClient(client);

        ctx.result("A new client has been registered...");
        ctx.status(201);
    };

    public Handler getClientByIdHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("clientId"));

        Client client = this.clientservice.getClientById(id);
        if (client == null) {
            ctx.status(404);
            ctx.result("NO SUCH CLIENT EXISTS");
        } else {
            String clientJSON = gson.toJson(client);
            ctx.status(200);
            ctx.result(clientJSON);
        }
    };

    public Handler updateClientHandler = (ctx) ->{

        int id = Integer.parseInt(ctx.pathParam("clientId"));
        Client oldClient = this.clientservice.getClientById(id);
        if(oldClient==null){
            ctx.status(404);
            ctx.result("NO SUCH CLIENT EXISTS");
        }else{
            String body = ctx.body();
            Client client = gson.fromJson(body, Client.class);
            client.setClientId(id);
            this.clientservice.updateClient(client);
            ctx.status(200);
            ctx.result("Client "+client.getClientId()+" has been updated.");
        }

    };

    public Handler deleteClientHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("clientId"));
        boolean deleted = this.clientservice.deleteClientById(id);
        if(deleted) {
            ctx.status(201);
            ctx.result("Client was successfully deleted.");
        } else {
            ctx.status(404);
            ctx.result("Uh oh, client was not deleted");
        }
    };

}
