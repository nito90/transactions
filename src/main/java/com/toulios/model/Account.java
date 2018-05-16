package com.toulios.model;

import java.io.Serializable;

/**
 * @author nikolaos.toulios
 *
 * A wrapper that represents the info of an Account
 */
public class Account implements Serializable{

    private static final long serialVersionUID = -2144034128153053194L;

    private String accountId;
    private String accountHoldersName;
    private Integer totalAmmount;

    public Account() {
    }

    public Account(String accountId, String accountHoldersName, Integer totalAmmount) {
        this.accountId = accountId;
        this.accountHoldersName = accountHoldersName;
        this.totalAmmount = totalAmmount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountHoldersName() {
        return accountHoldersName;
    }

    public void setAccountHoldersName(String accountHoldersName) {
        this.accountHoldersName = accountHoldersName;
    }

    public Integer getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(Integer totalAmmount) {
        this.totalAmmount = totalAmmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountId != null ? !accountId.equals(account.accountId) : account.accountId != null) return false;
        if (accountHoldersName != null ? !accountHoldersName.equals(account.accountHoldersName) : account.accountHoldersName != null)
            return false;
        return totalAmmount != null ? totalAmmount.equals(account.totalAmmount) : account.totalAmmount == null;
    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (accountHoldersName != null ? accountHoldersName.hashCode() : 0);
        result = 31 * result + (totalAmmount != null ? totalAmmount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", accountHoldersName='" + accountHoldersName + '\'' +
                ", totalAmmount=" + totalAmmount +
                '}';
    }
}
