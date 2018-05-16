package com.toulios.services;

import com.toulios.exceptions.account.AccountNotFoundException;
import com.toulios.exceptions.account.AccountValidationException;
import com.toulios.exceptions.transaction.TransactionValidationException;
import com.toulios.model.Account;
import com.toulios.model.Transaction;
import com.toulios.repository.AccountRepositoryImpl;
import com.toulios.repository.TransactionRepositoryImpl;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class TransactionServiceImplUnitTests {

    private TransactionServiceImpl transactionService;
    private TransactionRepositoryImpl transactionRepository;
    private AccountRepositoryImpl accountRepository;

    @Before
    public void setUp(){
        accountRepository = new AccountRepositoryImpl();
        transactionRepository = new TransactionRepositoryImpl();
        transactionService = new TransactionServiceImpl(transactionRepository, accountRepository);
    }

    @After
    public void tearDown(){
        accountRepository = null;
        transactionRepository = null;
        transactionService = null;
    }

    @Test(expected = TransactionValidationException.class)
    public void test_addTransaction_fail_null_item(){
        // given

        // when
        transactionService.addTransaction(null);

        // then
    }

    @Test(expected = TransactionValidationException.class)
    public void test_addTransaction_fail_null_from_account(){
        // given
        Account toAccount = new Account("acId", "acName", 10);
        Transaction transaction =  new Transaction(null, null, toAccount, 10);

        // when
        transactionService.addTransaction(transaction);
        // then
    }

    @Test(expected = TransactionValidationException.class)
    public void test_addTransaction_fail_null_to_account(){
        // given
        Account fromAccount = new Account("acId", "acName", 10);
        Transaction transaction =  new Transaction(null, fromAccount, null, 10);

        // when
        transactionService.addTransaction(transaction);
        // then
    }

    @Test(expected = TransactionValidationException.class)
    public void test_addTransaction_fail_null_transferingAmount(){
        // given
        Account fromAccount = new Account("acId", "acName", 10);
        Account toAccount = new Account("acId2", "acName2", 20);

        Transaction transaction =  new Transaction(null, fromAccount, toAccount, null);

        // when
        transactionService.addTransaction(transaction);
        // then
    }

    @Test(expected = TransactionValidationException.class)
    public void test_addTransaction_fail_transferingAmount_less_than_zero(){
        // given
        Account fromAccount = new Account("acId", "acName", 10);
        Account toAccount = new Account("acId2", "acName2", 20);

        Transaction transaction =  new Transaction(null, fromAccount, toAccount, -1);

        // when
        transactionService.addTransaction(transaction);
        // then
    }

    @Test(expected = TransactionValidationException.class)
    public void test_addTransaction_fail_null_accountIds(){
        // given
        Account fromAccount = new Account(null, "acName", 10);
        Account toAccount = new Account("acId2", "acName2", 20);

        Transaction transaction =  new Transaction(null, fromAccount, toAccount, 5);

        // when
        transactionService.addTransaction(transaction);

        // then
    }

    @Test(expected = TransactionValidationException.class)
    public void test_addTransaction_fail_not_null_transactionId(){
        // given
        Account fromAccount = new Account("acId", "acName", 10);
        Account toAccount = new Account("acId2", "acName2", 20);

        Transaction transaction =  new Transaction("trId", fromAccount, toAccount, 5);

        // when
        transactionService.addTransaction(transaction);

        // then
    }

    @Test(expected = AccountNotFoundException.class)
    public void test_addTransaction_fail_accounts_not_found(){
        // given
        Account fromAccount = new Account("acId", "acName", 10);
        Account toAccount = new Account("acId2", "acName2", 20);

        Transaction transaction =  new Transaction(null, fromAccount, toAccount, 5);

        // when
        transactionService.addTransaction(transaction);

        // then
    }

    @Test(expected = TransactionValidationException.class)
    public void test_addTransaction_fail_transferingAmount_is_greater(){
        // given
        Account fromAccount = new Account(null, "acName", 100);
        Account toAccount = new Account(null, "acName2", 100);

        fromAccount = accountRepository.add(fromAccount);
        toAccount = accountRepository.add(toAccount);

        Transaction transaction =  new Transaction(null, fromAccount, toAccount, 500);

        // when
        transactionService.addTransaction(transaction);

        // then
    }

    @Test
    public void test_addTransaction_success(){
        // given
        Account fromAccount = new Account(null, "acName", 100);
        Account toAccount = new Account(null, "acName2", 100);

        fromAccount = accountRepository.add(fromAccount);
        toAccount = accountRepository.add(toAccount);

        Transaction transaction =  new Transaction(null, fromAccount, toAccount, 50);

        // when
        transaction = transactionService.addTransaction(transaction);

        // then
        assertNotNull(transaction);
        assertNotNull(transaction.getTransactionId());
        assertFalse(transaction.getTransactionId().isEmpty());

        assertEquals(50, transaction.getFromAccount().getTotalAmmount().intValue());
        assertEquals(150, transaction.getToAccount().getTotalAmmount().intValue());
    }

    @Test(expected = TransactionValidationException.class)
    public void test_findTransaction_fail_null_key(){
        // given

        // when
        transactionService.findTransaction(null);

        // then
    }

    @Test
    public void test_findTransaction(){
        // given
        Account fromAccount = new Account(null, "acName", 100);
        Account toAccount = new Account(null, "acName2", 100);

        fromAccount = accountRepository.add(fromAccount);
        toAccount = accountRepository.add(toAccount);

        Transaction transaction =  new Transaction(null, fromAccount, toAccount, 50);


        transaction = transactionService.addTransaction(transaction);
        Pair<String, String> key = new Pair<>(fromAccount.getAccountId(), toAccount.getAccountId());

        // when
        Transaction result = transactionService.findTransaction(key);

        // then
        assertEquals(transaction, result);

    }
}
