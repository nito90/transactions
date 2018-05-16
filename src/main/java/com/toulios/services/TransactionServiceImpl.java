package com.toulios.services;

import com.toulios.api.transaction.TransactionService;
import com.toulios.exceptions.account.AccountNotFoundException;
import com.toulios.exceptions.account.AccountValidationException;
import com.toulios.exceptions.transaction.TransactionValidationException;
import com.toulios.model.Account;
import com.toulios.model.Transaction;
import com.toulios.repository.AccountRepositoryImpl;
import com.toulios.repository.TransactionRepositoryImpl;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepositoryImpl transactionRepository;
    private final AccountRepositoryImpl accountRepository;

    public TransactionServiceImpl(TransactionRepositoryImpl transactionRepository, AccountRepositoryImpl accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction addTransaction(Transaction item) {
        try {
            validateAdd(item);
            getTheAccounts(item);
            updateAccountsAmmount(item);
            transactionRepository.add(item);
        }catch (TransactionValidationException | AccountValidationException | AccountNotFoundException e){
            throw e;
        }
        return item;
    }

    @Override
    public void deleteTransaction(Pair<String, String> key) {
        if(key != null){
            transactionRepository.delete(key);
        }
    }

    @Override
    public Transaction findTransaction(Pair<String, String> id) {
       if(id == null){
           throw new TransactionValidationException("Identifier should not be null");
       }

       return transactionRepository.findOne(id);
    }

    @Override
    public Map<Pair<String, String>, Transaction> findAll() {
        return transactionRepository.findAll();
    }

    private void validateAdd(Transaction item) {
        if(item == null){
            throw new TransactionValidationException("Transaction should not be null");
        }

        if(StringUtils.isNotBlank(item.getTransactionId())){
            throw new TransactionValidationException("Transaction id should be empty");
        }

        validateAccounts(item);
    }


    private void validateAccounts(Transaction item) {
        if((item.getFromAccount() == null) || (item.getToAccount() == null)){
            throw new TransactionValidationException("Accounts info should not be null");
        }

        if(item.getTransferringAmmount() == null){
            throw new TransactionValidationException("Transfering ammount should not be null");
        }

        if(item.getTransferringAmmount() < 0){
            throw new TransactionValidationException("Transferring ammount should not be less than zero");
        }

        if(StringUtils.isBlank(item.getFromAccount().getAccountId())
                || StringUtils.isBlank(item.getToAccount().getAccountId())){
            throw new TransactionValidationException("AccountIds from accounts should not my empty");
        }

        if(StringUtils.isBlank(item.getFromAccount().getAccountHoldersName())
                || StringUtils.isBlank(item.getToAccount().getAccountHoldersName())){
            throw new TransactionValidationException("Account name from accounts should not my empty");
        }
    }

    private void getTheAccounts(Transaction item) {
        Account fromAccount = accountRepository
                .findByNameAndAccountId(item.getFromAccount().getAccountHoldersName(), item.getFromAccount().getAccountId());
        Account toAccount = accountRepository
                .findByNameAndAccountId(item.getToAccount().getAccountHoldersName(), item.getToAccount().getAccountId());
        item.setFromAccount(fromAccount);
        item.setToAccount(toAccount);
    }

    private void updateAccountsAmmount(Transaction item){
        Account fromAccount = item.getFromAccount();
        Account toAccount = item.getToAccount();

        if(fromAccount.getTotalAmmount() < item.getTransferringAmmount()){
            throw new TransactionValidationException("Transfering amount should be less than the from accounts total amount");
        }

        fromAccount.setTotalAmmount(fromAccount.getTotalAmmount() - item.getTransferringAmmount());
        toAccount.setTotalAmmount(toAccount.getTotalAmmount() + item.getTransferringAmmount());

        accountRepository.update(fromAccount);
        accountRepository.update(toAccount);

        item.setFromAccount(fromAccount);
        item.setToAccount(toAccount);
    }
}
