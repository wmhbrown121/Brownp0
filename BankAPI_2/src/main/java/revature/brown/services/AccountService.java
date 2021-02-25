package revature.brown.services;

import revature.brown.entities.Account;

import java.util.Set;

public interface AccountService {

    Account registerAccount(Account account);

    Set<Account> getAllAccounts();
    Set<Account> getAccountsByClientId(int clientId);
    Account getAccountsByClientId(int clientId, int accountId);
    Set<Account> getAccountsWithinRange(int clientId, double minBalance, double maxBalance);

    Account updateAccount(Account account);

    boolean deleteAccountById(int accountId);
}
