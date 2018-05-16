package com.toulios.api.account;

import com.toulios.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This service handles the CRUD opperations for Account model
 */
public interface AccountService {

    /**
     * Add a new Account object
     *
     * @param account The new object
     * @return the new object with its initial account identifier
     */
    Account addAccount(Account account);

    /**
     * Update the information of an Account object
     *
     * @param account The object which will be updated
     * @return The updated object
     */
    Account updateAccount(Account account);

    /**
     * Return all the accounts based to the identifier
     *
     * @param id
     * @return A list with every account of the specified id
     */
    List<Account> getAllAccounts(String id);

    /**
     * Returns all the accounts
     *
     * @return a Map of all the accounts
     */
    Map<String, ArrayList<Account>> getAccounts();

    /**
     * Delete an account for the specified information
     * @param id Identifier for a banch of accounts
     * @param accountId The identifier of a specified account
     */
    void deleteAccount(String id, String accountId);
}
