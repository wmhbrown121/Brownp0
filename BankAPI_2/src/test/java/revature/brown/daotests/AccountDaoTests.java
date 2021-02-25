package revature.brown.daotests;

import org.junit.jupiter.api.*;
import revature.brown.daos.*;
import revature.brown.entities.Account;
import revature.brown.entities.Client;

import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountDaoTests {

    private static ClientDAO clientDAO = new ClientDaoPostgres();
    private static AccountDAO accountDAO = new AccountDaoPostgres();
    private static Account testAccount = null;

    @Test
    @Order(1)
    void create_account(){
        Client c1 = new Client(0,"Hogan","Brown");
        Client c2 = new Client(0,"Michael","McCracken");
        Client c3 = new Client(0, "Hayden", "Brown");
        clientDAO.createClient(c1);
        clientDAO.createClient(c2);
        clientDAO.createClient(c3);
        Account a1 = new Account(0, 100, 1);
        Account a2 = new Account(0,1000,1);
        Account a3 = new Account(0,2000,2);
        Account a4 = new Account(0,3000,3);
        Account a5 = new Account(0,4000,3);
        accountDAO.createAccount(a1);
        accountDAO.createAccount(a2);
        accountDAO.createAccount(a3);
        accountDAO.createAccount(a4);
        accountDAO.createAccount(a5);


        testAccount = a1;
        Assertions.assertNotEquals(0,a1.getAccountId());

    }

    @Test
    @Order(2)
    void get_all_accounts(){


        Set<Account> allAccounts = accountDAO.getAllAccounts();
        Assertions.assertTrue(allAccounts.size()>3);
        System.out.println(allAccounts);
        System.out.println("There are "+allAccounts.size()+" accounts.");

    }

    @Test
    @Order(3)
    void get_account_by_id(){
        int id = testAccount.getAccountId();
        Account account = accountDAO.getAccountById(id);

        Assertions.assertEquals(testAccount.getAccountBalance(),account.getAccountBalance());
        System.out.println("The account balance for account "+id+" is: "+testAccount.getAccountBalance());

    }

    @Test
    @Order(4)
    void update_account(){

        Account oldAct = accountDAO.getAccountById(testAccount.getAccountId());
        System.out.println("Old balance for account "+oldAct.getAccountId()+" was "+oldAct.getAccountBalance());
        oldAct.setAccountBalance(oldAct.getAccountBalance()+500);
        accountDAO.updateAccount(oldAct);
        Account newAct = accountDAO.getAccountById(testAccount.getAccountId());
        Assertions.assertEquals(600,newAct.getAccountBalance());
        System.out.println("New balance for account "+newAct.getAccountId()+" is "+newAct.getAccountBalance());

    }

    @Test
    @Order(5)
    void delete_account_by_id(){
        int id = testAccount.getAccountId();
        boolean result = accountDAO.deleteAccount(id);
        Assertions.assertTrue(result);
        System.out.println("Account "+id+" was successfully deleted");
        Set<Account> remainingAccounts = accountDAO.getAllAccounts();
        System.out.println("There are now "+remainingAccounts.size()+" accounts remaining.");
    }


}
