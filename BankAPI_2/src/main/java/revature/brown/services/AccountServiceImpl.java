package revature.brown.services;

import org.apache.log4j.Logger;
import revature.brown.daos.AccountDAO;
import revature.brown.daos.ClientDaoPostgres;
import revature.brown.entities.Account;

import java.util.HashSet;
import java.util.Set;

public class AccountServiceImpl implements AccountService{

//    private static Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());
//    // when the logger writes it will say what class the log was from
    private static Logger logger = Logger.getLogger(AccountServiceImpl.class);
    private ClientService clientService = new ClientServiceImpl(new ClientDaoPostgres());
    private AccountDAO adao;

    public AccountServiceImpl(AccountDAO accountDAO){ this.adao = accountDAO;}

    @Override
    public Account registerAccount(Account account) {
        int clientId = account.getClient_Id();
        this.adao.createAccount(account);
        logger.info("Registered: account " + account.getAccountId() + " for client "+clientId);
        return account;
    }

    @Override
    public Set<Account> getAllAccounts() {
        logger.info("Retrieved: all accounts");
        return this.adao.getAllAccounts();
    }

    @Override
    public Set<Account> getAccountsByClientId(int clientId) {

        Set<Account> allAccounts = this.adao.getAllAccounts();
        Set<Account> clientAccounts = new HashSet<Account>();

        for (Account a : allAccounts) {
            if (a.getClient_Id() == clientId) {
                clientAccounts.add(a);
            }
        }
        logger.info("Retrieved: all accounts for client "+clientId);
        return clientAccounts;

    }

    @Override
    public Account getAccountsByClientId(int clientId, int accountId) {
        Set<Account> allClientAccounts = this.getAccountsByClientId(clientId);

        for (Account a : allClientAccounts) {
            if (a.getAccountId() == accountId) {
                logger.info("Retrieved: account "+a.getAccountId()+" for client "+a.getClient_Id());
                return a;
            }
        }
        return null;
    }

    @Override
    public Set<Account> getAccountsWithinRange(int clientId, double minBalance, double maxBalance) {
        Set<Account> retrievedAccounts = new HashSet<Account>();
        Set<Account> clientAccounts = this.getAccountsByClientId(clientId);
        for (Account a : clientAccounts) {
            if (a.getAccountBalance() >= minBalance && a.getAccountBalance() <= maxBalance) {
                retrievedAccounts.add(a);
            }
        }
        logger.info("Retrieved: accounts for client "+clientId+" within range: "+minBalance+" - "+maxBalance);
        return retrievedAccounts;
    }

    @Override
    public Account updateAccount(Account account) {
        this.adao.updateAccount(account);
        logger.info("Updated: account "+account.getAccountId()+" for client "+account.getClient_Id());
        return account;
    }

    @Override
    public boolean deleteAccountById(int accountId) {
        logger.info("Deleted: account "+accountId);
        return this.adao.deleteAccount(accountId);
    }
}
