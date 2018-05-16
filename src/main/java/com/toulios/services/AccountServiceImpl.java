package com.toulios.services;

import com.toulios.api.account.AccountService;
import com.toulios.exceptions.account.AccountValidationException;
import com.toulios.exceptions.transaction.TransactionValidationException;
import com.toulios.model.Account;
import com.toulios.repository.AccountRepositoryImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountServiceImpl implements AccountService{

    private final AccountRepositoryImpl accountRepository;

    public AccountServiceImpl(AccountRepositoryImpl accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account addAccount(Account item) {
        try {
            validateAdd(item);

            item = accountRepository.add(item);
        }catch (TransactionValidationException e){
            throw e;
        }
        return item;
    }

    @Override
    public Account updateAccount(Account item) {
        try {
            validateUpdate(item);
            item = accountRepository.update(item);
        }catch (TransactionValidationException e){
            throw e;
        }
        return item;
    }

    @Override
    public List<Account> getAllAccounts(String id) {
        if(StringUtils.isNotBlank(id)){
            return accountRepository.findAllAccounts(id);
        }
        else{
            return new ArrayList<>();
        }
    }

    @Override
    public Map<String, ArrayList<Account>> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(String id, String accountId) {
        if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(accountId)){
            accountRepository.delete(id, accountId);
        }
    }

    public Account findByNameAndAccountId(String accountName, String accountId){
        if(StringUtils.isBlank(accountName) || StringUtils.isBlank(accountId)){
            throw new AccountValidationException("Validation Error Message");
        }

        return accountRepository.findByNameAndAccountId(accountName, accountId);

    }

    private void validateAdd(Account item) {
        validateGeneralAccountItem(item);

        if(StringUtils.isNotBlank(item.getAccountId())){
            throw new AccountValidationException("AccountId must be null or empty");
        }
    }

    private void validateGeneralAccountItem(Account item) {
        if(item == null){
            throw new AccountValidationException("Account item must not be null.");
        }

        if(item.getTotalAmmount() == null){
            throw new AccountValidationException("Total ammount should not be null");
        }

        if(item.getTotalAmmount() < 0){
            throw new AccountValidationException("Total ammount should be greater than zero");
        }
    }

    private void validateUpdate(Account item) {
        validateGeneralAccountItem(item);

        if(StringUtils.isBlank(item.getAccountId())){
            throw new AccountValidationException("AccountId must not be null or empty");
        }
    }

}
