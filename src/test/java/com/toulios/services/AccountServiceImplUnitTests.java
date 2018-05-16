package com.toulios.services;

import com.toulios.exceptions.account.AccountNotFoundException;
import com.toulios.exceptions.account.AccountValidationException;
import com.toulios.model.Account;
import com.toulios.repository.AccountRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class AccountServiceImplUnitTests {

    private AccountServiceImpl accountService;
    private AccountRepositoryImpl accountRepository;

    @Before
    public void setUp(){
        accountRepository = new AccountRepositoryImpl();
        accountService = new AccountServiceImpl(accountRepository);
    }

    @After
    public void tearDown(){
        accountService = null;
        accountRepository = null;
    }

    @Test(expected = AccountValidationException.class)
    public void test_addAccount_fail_null_item(){

        // given

        // when
        accountService.addAccount(null);

        // then

    }

    @Test(expected = AccountValidationException.class)
    public void test_addAccount_fail_account_id_with_value(){
        // given
        Account account = new Account();
        account.setAccountId("testAccountId");

        // when
        accountService.addAccount(account);

        // then

    }

    @Test(expected = AccountValidationException.class)
    public void test_addAccount_fail_null_ammount(){
        // given
        Account account = new Account(null, "holder", null);

        // when
        accountService.addAccount(account);

        // then

    }

    @Test(expected = AccountValidationException.class)
    public void test_addAccount_fail_ammount_less_than_zero(){
        // given
        Account account = new Account(null, "holder", -1);

        // when
        accountService.addAccount(account);

        // then

    }

    @Test
    public void test_addAccount_success(){
        // given
        Account account = new Account(null, "name", 10);

        // when
        Account result = accountService.addAccount(account);

        // then
        assertNotNull(result.getAccountId());
        assertEquals(result.getAccountHoldersName(), account.getAccountHoldersName());
    }

    @Test(expected = AccountValidationException.class)
    public void test_updateAccount_fail_null_item(){

        // given

        // when
        accountService.updateAccount(null);

        // then

    }

    @Test(expected = AccountValidationException.class)
    public void test_updateAccount_fail_account_id_without_value(){
        // given
        Account account = new Account();
        account.setAccountId("");

        // when
        accountService.updateAccount(account);

        // then

    }

    @Test(expected = AccountValidationException.class)
    public void test_updateAccount_fail_null_ammount(){
        // given
        Account account = new Account(null, "holder", null);

        // when
        accountService.updateAccount(account);

        // then

    }

    @Test(expected = AccountValidationException.class)
    public void test_updateAccount_fail_ammount_less_than_zero(){
        // given
        Account account = new Account(null, "holder", -1);

        // when
        accountService.updateAccount(account);

        // then

    }

    @Test
    public void test_updateAccount_success(){
        // given
        Account account = new Account(null, "name", 10);
        account = accountService.addAccount(account);
        account.setTotalAmmount(100);

        // when
        account = accountService.updateAccount(account);

        // then
        assertNotNull(account.getAccountId());
        assertNotEquals(10, account.getTotalAmmount().intValue());
    }

    @Test
    public void test_delete_delete_with_actual_value(){
        // given
        Account account = new Account(null, "name", 10);
        account = accountService.addAccount(account);

        Account actualAccount = accountService.findByNameAndAccountId(account.getAccountHoldersName(), account.getAccountId());
        assertNotNull(actualAccount);

        // when
        accountService.deleteAccount(actualAccount.getAccountHoldersName(), account.getAccountId());

        // then
        try{
            accountService.findByNameAndAccountId(actualAccount.getAccountHoldersName(),actualAccount.getAccountId());
        }catch (AccountNotFoundException e){
            assertEquals("Account not found", e.getMessage());
        }
    }

    @Test
    public void test_getAccounts_success_emptyList_without_entry(){
        // given

        // when
        Map<String, ArrayList<Account>> accounts = accountService.getAccounts();

        // then
        assertTrue(accounts.isEmpty());
    }

    @Test
    public void test_getAccounts_success_with_entry(){
        // given
        Account account = new Account(null, "name", 10);
        accountService.addAccount(account);

        // when
        Map<String, ArrayList<Account>> accounts = accountService.getAccounts();

        // then
        assertFalse(accounts.isEmpty());
    }

    @Test(expected = AccountValidationException.class)
    public void test_findByNameAndAccountId_fail_empty_accountName(){
        // given

        // when
        accountService.findByNameAndAccountId("", "acId");

        // then
    }

    @Test(expected = AccountValidationException.class)
    public void test_findByNameAndAccountId_fail_empty_accountId(){
        // given

        // when
        accountService.findByNameAndAccountId("acName", "");

        // then
    }

    @Test(expected = AccountNotFoundException.class)
    public void test_findByNameAndAccountId_fail_not_found(){
        // given

        // when
        accountService.findByNameAndAccountId("acName", "acId");

        // then
    }

    @Test
    public void test_findByNameAndAccountId_success(){

        // given
        Account account = new Account(null, "name", 10);
        account = accountService.addAccount(account);

        // when
        Account result = accountService.findByNameAndAccountId(account.getAccountHoldersName(), account.getAccountId());

        // then
        assertEquals(result.getAccountHoldersName(), account.getAccountHoldersName());
        assertEquals(result.getAccountId(), account.getAccountId());
    }

}
