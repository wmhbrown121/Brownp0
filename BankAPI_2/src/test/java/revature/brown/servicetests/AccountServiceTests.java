package revature.brown.servicetests;

import org.junit.jupiter.api.*;
import revature.brown.daos.AccountDaoLocal;
import revature.brown.daos.AccountDaoPostgres;
import revature.brown.daos.ClientDaoPostgres;
import revature.brown.entities.Account;
import revature.brown.entities.Client;
import revature.brown.services.AccountService;
import revature.brown.services.AccountServiceImpl;
import revature.brown.services.ClientService;
import revature.brown.services.ClientServiceImpl;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTests {

    private static ClientService clientService = new ClientServiceImpl(new ClientDaoPostgres());
    private static AccountService aserv = new AccountServiceImpl(new AccountDaoPostgres());
    private static Account testAccount = null;

    @Test
    @Order(1)
    void setup(){
        Client c1 = new Client(0,"Paul","McCartney");
        Client c2 = new Client(0,"John","Lennon");
        Client c3 = new Client(0,"George","Harrison");
        Client c4 = new Client(0,"Ringo","Star");
        clientService.registerClient(c1);
        clientService.registerClient(c2);
        clientService.registerClient(c3);
        clientService.registerClient(c4);
    }

    @Test
    @Order(1)
    void register_account(){
        Account a1 = new Account(0, 10000, 1);
        Account a2 = new Account(0, 150, 1);
        Account a3 = new Account(0, 500, 2);
        Account a4 = new Account(0, 1000, 3);
        Account a5 = new Account(0, 400, 1);
        Account a6 = new Account(0, 2000, 1);
        aserv.registerAccount(a1);
        aserv.registerAccount(a2);
        aserv.registerAccount(a3);
        aserv.registerAccount(a4);
        aserv.registerAccount(a5);
        aserv.registerAccount(a6);

        testAccount = a1;

        Assertions.assertNotEquals(0, a1.getAccountId());
        System.out.println("Account successfully registered!");
        System.out.println(testAccount);
    }

    @Test
    @Order(2)
    void get_accounts_by_client_id(){
        Set<Account> clientAccounts = aserv.getAccountsByClientId(testAccount.getClient_Id());

        Assertions.assertEquals(4,clientAccounts.size());
        System.out.println("Accounts retrieved for client "+testAccount.getClient_Id());
        System.out.println(clientAccounts);

    }

    @Test
    @Order(3)
    void get_all_accounts(){
        Set<Account> allAccounts = aserv.getAllAccounts();

        Assertions.assertEquals(6,allAccounts.size());
        System.out.println("All "+allAccounts.size()+" accounts retrieved...");
    }

    @Test
    @Order(4)
    void get_account_by_id(){
        Account clientsAccount = aserv.getAccountsByClientId(testAccount.getClient_Id(), testAccount.getAccountId());
        Assertions.assertEquals(1,clientsAccount.getClient_Id());
        Assertions.assertEquals(1,clientsAccount.getAccountId());
        System.out.println("Retrieved account "+clientsAccount.getAccountId()+" from client "+clientsAccount.getClient_Id());
    }

    @Test
    @Order(5)
    void get_accounts_by_client_id_within_range(){
        Set<Account> retrievedAccounts = this.aserv.getAccountsWithinRange(2, 400,2000);
        Assertions.assertEquals(1,retrievedAccounts.size());
        System.out.println("The retrieved accounts for client "+1);
        System.out.println(retrievedAccounts);
    }

    @Test
    @Order(6)
    void update_account_by_id(){
        Account account = new Account(testAccount.getAccountId(), 500000, 1);
        Account newAccount = aserv.updateAccount(account);

        System.out.println(newAccount);
        Assertions.assertEquals(500000,newAccount.getAccountBalance());
    }

    @Test
    @Order(7)
    void delete_account_by_id(){
        int id = testAccount.getAccountId();
        boolean result = aserv.deleteAccountById(id);
        Assertions.assertTrue(result);
        System.out.println("Account "+id+" successfully deleted!");

    }

}
