package revature.brown.daos;

import revature.brown.entities.Account;
import revature.brown.entities.Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccountDaoLocal implements AccountDAO{

    private static Map<Integer,Account> accountTable = new HashMap<Integer, Account>();
    private static int accountIdMaker = 0;

    @Override
    public Account createAccount(Account account) {
        account.setAccountId(++accountIdMaker);
        accountTable.put(account.getAccountId(),account);
        return account;
    }

    @Override
    public Set<Account> getAllAccounts() {
        Set<Account> allAccounts = new HashSet<Account>(accountTable.values());
        return allAccounts;
    }

    @Override
    public Account getAccountById(int accountId) { return accountTable.get(accountId); }

    @Override
    public Account updateAccount(Account account) {
        return accountTable.put(account.getAccountId(),account);
    }

    @Override
    public boolean deleteAccount(int accountId) {
        Account account = accountTable.remove(accountId);
        if(account==null){
            return false;
        }else{
            return true;
        }
    }
}
