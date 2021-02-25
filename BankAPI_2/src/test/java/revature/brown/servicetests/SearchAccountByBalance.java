package revature.brown.servicetests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import revature.brown.daos.*;
import revature.brown.entities.Account;
import revature.brown.entities.Client;
import revature.brown.services.AccountService;
import revature.brown.services.AccountServiceImpl;
import revature.brown.services.ClientService;
import revature.brown.services.ClientServiceImpl;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class SearchAccountByBalance {

    @Mock
    AccountDAO accountDAO = null;

    private AccountService accountService = new AccountServiceImpl(new AccountDaoPostgres());
    private static ClientService clientService = new ClientServiceImpl(new ClientDaoPostgres());
//    private Client testClient = null;

    @BeforeEach
    void setup(){
        Client c1 = new Client(0,"Don","Fielding");
        Client c2 = new Client(0,"Mike","Conner");
        clientService.registerClient(c1);
        clientService.registerClient(c2);

        Account a1 = new Account(0, 100, 1);
        Account a2 = new Account(0, 400, 1);
        Account a3 = new Account(0, 2000, 1);
        Account a4 = new Account(0, 1000, 2);
        Set<Account> accountSet = new HashSet<Account>();
        accountSet.add(a1);
        accountSet.add(a2);
        accountSet.add(a3);
        accountSet.add(a4);

        Mockito.when(accountDAO.getAllAccounts()).thenReturn(accountSet);

        this.accountService = new AccountServiceImpl(this.accountDAO);
    }

    @Test
    void get_accounts_within_range(){
        Set<Account> retrievedAccounts = this.accountService.getAccountsWithinRange(1,400,2000);
        Assertions.assertEquals(2,retrievedAccounts.size());
        System.out.println("The retrieved accounts for client "+1);
        System.out.println(retrievedAccounts);
    }


}
