package com.toulios.repository;

import com.toulios.api.account.AccountRepository;
import com.toulios.exceptions.account.AccountNotFoundException;
import com.toulios.exceptions.account.AccountValidationException;
import com.toulios.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * @author nikolaos.toulios
 *
 * In memory repository to handle Account data
 */
public class AccountRepositoryImpl implements AccountRepository{

    private static final String KEY_PREFIX = "account-";

    /**
     * Key pair ( holder name, accounts for this name )
     */
    private final ConcurrentHashMap<String, ArrayList<Account>> accountMap;

    public AccountRepositoryImpl() {
        this.accountMap = new ConcurrentHashMap<>();
    }

    @Override
    public Account add(Account item) {
        try {

            String accountId = new StringBuffer().append(KEY_PREFIX+ UUID.randomUUID()).toString();
            item.setAccountId(accountId);

            String key = item.getAccountHoldersName();

            if(!accountMap.containsKey(key)) {
                ArrayList<Account> accounts = new ArrayList<>();
                accounts.add(item);
                accountMap.put(key, accounts);
            }
            else{
                accountMap.get(key).add(item);
            }

        }catch (AccountValidationException e){
            throw e;
        }
        return item;
    }

    @Override
    public Account update(Account item) {
        try {
            String key = item.getAccountHoldersName();
            if(accountMap.containsKey(key)){
                ArrayList<Account> accounts = accountMap.get(key);
                Account accountValue = accountMap.get(key)
                        .stream()
                        .filter(account -> account.getAccountId().equals(item.getAccountId()))
                        .collect(Collectors.toList()).get(0);

                if(accountValue != null){
                    accounts.remove(accountValue);
                    accounts.add(item);
                    accountMap.put(key, accounts);
                }
            }
            else{
                throw new AccountNotFoundException("Account not found");
            }
        }catch (AccountNotFoundException e){
            throw e;
        }
        return item;
    }

    public void delete(String id, String accountId) {
        ArrayList<Account> accounts = accountMap.get(id);
        if(accounts != null){
            accountMap.get(id).removeIf(account -> accountId.equals(account.getAccountId()));
        }
    }

    public List<Account> findAllAccounts(String id) {
        List<Account> accounts = accountMap.get(id);
        if (null == accounts) {
            new ArrayList<>();
        }
        return accounts;
    }

    public Account findByNameAndAccountId(String accountName, String accountId){

        List<Account> accounts = accountMap.get(accountName);
        if (null == accounts) {
            throw new AccountNotFoundException("Account not found");
        }
        Account foundAccount = null;
        for(Account account : accounts){
            if(accountId.equals(account.getAccountId())){
                foundAccount = account;
                break;
            }
        }
        if(foundAccount == null){
            throw new AccountNotFoundException("Account not found");
        }
        return foundAccount;
    }

    public Map<String, ArrayList<Account>> findAll() {
        return accountMap;
    }

}
