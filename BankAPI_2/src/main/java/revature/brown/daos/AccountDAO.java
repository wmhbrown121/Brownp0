package revature.brown.daos;

import revature.brown.entities.Account;

import java.util.Set;

public interface AccountDAO {

    Account createAccount(Account account);

    Set<Account> getAllAccounts();
    Account getAccountById(int accountId);

    Account updateAccount(Account account);

    boolean deleteAccount(int accountId);

}
