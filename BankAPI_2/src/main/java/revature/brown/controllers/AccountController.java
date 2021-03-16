package revature.brown.controllers;

import com.google.gson.Gson;
import io.javalin.http.Handler;
import revature.brown.daos.AccountDaoPostgres;
import revature.brown.daos.ClientDaoPostgres;
import revature.brown.entities.Account;
import revature.brown.entities.Client;
import revature.brown.services.AccountService;
import revature.brown.services.AccountServiceImpl;
import revature.brown.services.ClientService;
import revature.brown.services.ClientServiceImpl;

import java.util.Set;

public class AccountController {

    private static Logger logger = Logger.getLogger(AccountServiceImpl.class);

    private AccountService accountService = new AccountServiceImpl(new AccountDaoPostgres());
    private ClientService clientService = new ClientServiceImpl(new ClientDaoPostgres());
    Gson gson = new Gson();

    // POST/clients/:clientId/accounts
    public Handler createAccountHandler = (ctx) -> {
        // Exception for client not found needs to be implemented
        int clientId = Integer.parseInt(ctx.pathParam("clientId"));

        if(accountService.getAccountsByClientId(clientId)==null){
            ctx.status(404);
            ctx.result("Client ID not found. Accounts can only be created for registered clients.");
        }else {
            String body = ctx.body();
            Account account = gson.fromJson(body, Account.class);
            account.setClient_Id(clientId);
            this.accountService.registerAccount(account);
            String json = gson.toJson(account);
            ctx.result(json);
            ctx.status(201);
        }
    };

    // GET/clients/:clientId/accounts
    public Handler getAccountsByClientIdHandler = (ctx) -> {

        int id = Integer.parseInt(ctx.pathParam("clientId"));
        String maxStr = ctx.queryParam("amountLessThan", "NONE");
        String minStr = ctx.queryParam("amountGreaterThan", "NONE");

        if(accountService.getAccountsByClientId(id)==null){
            ctx.status(404);
            ctx.result("CLIENT ID NOT FOUND");
        }else if (maxStr.equals("NONE") && minStr.equals("NONE")){
            Set<Account> clientAccounts = this.accountService.getAccountsByClientId(id);
            String json = gson.toJson(clientAccounts);
            ctx.result(json);
            ctx.status(200);
        }else{
            double max = Double.parseDouble(maxStr);
            double min = Double.parseDouble(minStr);
            Set<Account> accountSet = this.accountService.getAccountsWithinRange(id,min,max);
            String json = gson.toJson(accountSet);
            ctx.result(json);
            ctx.status(201);
        }
    };

    public Handler getAccountByIdHandler = (ctx) -> {

        int clientId = Integer.parseInt(ctx.pathParam("clientId"));
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));

        if(clientService.getClientById(clientId)==null){
            ctx.status(404);
            ctx.result("CLIENT ID NOT FOUND");
        }else if(accountService.getAccountsByClientId(clientId,accountId)==null){
            ctx.status(404);
            ctx.result("ACCOUNT ID NOT FOUND");
        }else{
            Account account = this.accountService.getAccountsByClientId(clientId,accountId);
            String json = gson.toJson(account);
            ctx.result(json);
            ctx.status(201);
        }
    };
    public Handler updateAccountHandler = (ctx) -> {

        int clientId = Integer.parseInt(ctx.pathParam("clientId"));
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));

        if(clientService.getClientById(clientId)==null){
            ctx.status(404);
            ctx.result("CLIENT ID NOT FOUND");
        }else if(accountService.getAccountsByClientId(clientId,accountId)==null){
            ctx.status(404);
            ctx.result("ACCOUNT ID NOT FOUND");
        }else {
            String body = ctx.body();
            Account newAccount = gson.fromJson(body, Account.class);
            newAccount.setAccountId(accountId);
            newAccount.setClient_Id(clientId);
            Account updatedAccount = this.accountService.updateAccount(newAccount);
            String json = gson.toJson(updatedAccount);
            ctx.result(json);
        }
    };

    public Handler deleteAccountHandler = (ctx) -> {

        int clientId = Integer.parseInt(ctx.pathParam("clientId"));
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));

        if(clientService.getClientById(clientId)==null){
            ctx.status(404);
            ctx.result("CLIENT ID NOT FOUND");
        }else if(accountService.getAccountsByClientId(clientId,accountId)==null) {
            ctx.status(404);
            ctx.result("ACCOUNT ID NOT FOUND");
        }else {
            boolean deleted = this.accountService.deleteAccountById(accountId);
            if(deleted){
                ctx.result("Account "+accountId+" has been deleted.");
                ctx.status(200);
            }
        }
    };



}
