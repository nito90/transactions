package com.toulios.model;

import java.io.Serializable;

/**
 * @author nikolaos.toulios
 *
 * A wrapper that represents the info of a Transaction
 */
public class Transaction implements Serializable {

    private static final long serialVersionUID = 4855839326344839970L;

    private String transactionId;
    private Account fromAccount;
    private Account toAccount;
    private Integer transferringAmmount;

    public Transaction() {
    }

    public Transaction(String transactionId, Account fromAccount, Account toAccount, Integer transferringAmmount) {
        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transferringAmmount = transferringAmmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public Integer getTransferringAmmount() {
        return transferringAmmount;
    }

    public void setTransferringAmmount(Integer transferringAmmount) {
        this.transferringAmmount = transferringAmmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;
        if (fromAccount != null ? !fromAccount.equals(that.fromAccount) : that.fromAccount != null) return false;
        if (toAccount != null ? !toAccount.equals(that.toAccount) : that.toAccount != null) return false;
        return transferringAmmount != null ? transferringAmmount.equals(that.transferringAmmount) : that.transferringAmmount == null;
    }

    @Override
    public int hashCode() {
        int result = transactionId != null ? transactionId.hashCode() : 0;
        result = 31 * result + (fromAccount != null ? fromAccount.hashCode() : 0);
        result = 31 * result + (toAccount != null ? toAccount.hashCode() : 0);
        result = 31 * result + (transferringAmmount != null ? transferringAmmount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", transferringAmmount=" + transferringAmmount +
                '}';
    }
}
