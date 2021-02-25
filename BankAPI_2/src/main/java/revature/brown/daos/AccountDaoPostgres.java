package revature.brown.daos;

import revature.brown.entities.Account;
import revature.brown.utils.ConnectionUtil;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AccountDaoPostgres implements AccountDAO{

    @Override
    public Account createAccount(Account account) {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into account (account_balance, account_client_id) values (?,?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, account.getAccountBalance());
            ps.setInt(2, account.getClient_Id());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("account_id");
            account.setAccountId(key);
            return account;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

            return null;
        }
    }

    @Override
    public Set<Account> getAllAccounts() {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            Set<Account> accounts = new HashSet<Account>();
            while(rs.next()){
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setAccountBalance(rs.getDouble("account_balance"));
                account.setClient_Id(rs.getInt("account_client_id"));
                accounts.add(account);
            }

            return accounts;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Account getAccountById(int accountId) {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,accountId);
            ResultSet rs = ps.executeQuery();
            rs.next();

            Account account = new Account();
            account.setAccountId(rs.getInt("account_id"));
            account.setAccountBalance(rs.getDouble("account_balance"));
            account.setClient_Id(rs.getInt("account_client_id"));

            return account;

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Account updateAccount(Account account) {

        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "update account set account_balance=?,account_client_id=? where account_id=?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, account.getAccountBalance());
            ps.setInt(2, account.getClient_Id());
            ps.setInt(3, account.getAccountId());
            ps.executeUpdate();
            return account;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean deleteAccount(int accountId) {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from account where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, accountId);
            ps.execute();
            return true;
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
            return false;
        }
    }
}
