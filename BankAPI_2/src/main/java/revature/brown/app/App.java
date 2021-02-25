package revature.brown.app;

import io.javalin.Javalin;
import revature.brown.controllers.AccountController;
import revature.brown.controllers.ClientController;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        ClientController clientController = new ClientController();
        AccountController accountController = new AccountController();

        app.post("/clients",clientController.createClientHandler);
        app.get("/clients",clientController.getAllClientsHandler);
        app.get("/clients/:clientId",clientController.getClientByIdHandler);
        app.put("/clients/:clientId",clientController.updateClientHandler);
        app.delete("/clients/:clientId",clientController.deleteClientHandler);


        app.post("/clients/:clientId/accounts",accountController.createAccountHandler);
//        "/clients/:clientId/accounts?amountLessThan=2000&amountGreaterThan=400"
        app.get("/clients/:clientId/accounts",accountController.getAccountsByClientIdHandler);
        app.get("/clients/:clientId/accounts/:accountId",accountController.getAccountByIdHandler);
        app.put("/clients/:clientId/accounts/:accountId",accountController.updateAccountHandler);
        app.delete("/clients/:clientId/accounts/:accountId",accountController.deleteAccountHandler);

        app.start();


    }


}
