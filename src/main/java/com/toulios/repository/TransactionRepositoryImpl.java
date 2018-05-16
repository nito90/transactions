package com.toulios.repository;

import com.toulios.api.transaction.TransactionRepository;
import com.toulios.exceptions.transaction.TransactionNotFoundException;
import com.toulios.exceptions.transaction.TransactionValidationException;
import com.toulios.model.Transaction;
import javafx.util.Pair;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionRepositoryImpl implements TransactionRepository{

    private static final String KEY_PREFIX = "transaction-";

    private static final String NOT_FOUND_ERROR_MESSAGE = "Not found";
    private static final String ALREADY_EXIST_ERROR_MESSAGE = "Already error";

    /**
     * Key pair ( holder name, accounts for this name )
     */
    private final ConcurrentHashMap<Pair<String , String>, Transaction> transactionMap;

    public TransactionRepositoryImpl() {
        this.transactionMap = new ConcurrentHashMap<>();
    }

    @Override
    public Transaction add(Transaction item) {

        try {

            String transactionId = new StringBuffer().append(KEY_PREFIX+ UUID.randomUUID()).toString();
            item.setTransactionId(transactionId);

            Pair<String, String> key = new Pair<>(item.getFromAccount().getAccountId(), item.getToAccount().getAccountId());

            if(!transactionMap.containsKey(key)) {
                transactionMap.put(key, item);
            }
            else{
                throw new TransactionValidationException(ALREADY_EXIST_ERROR_MESSAGE);
            }

        }catch (TransactionValidationException e){
            throw e;
        }
        return item;
    }

    @Override
    public Transaction update(Transaction item) {
        try {
            Pair<String, String> key = new Pair<>(item.getFromAccount().getAccountId(), item.getToAccount().getAccountId());
            if(transactionMap.containsKey(key)) {
                transactionMap.put(key, item);
            }
            else{
                throw new TransactionNotFoundException(NOT_FOUND_ERROR_MESSAGE);
            }
        }catch (TransactionValidationException | TransactionNotFoundException e){
            throw e;
        }
        return item;
    }

    public void delete(Pair<String, String> id) {
        if(transactionMap.containsKey(id)) {
            transactionMap.remove(id);
        }
    }

    public Transaction findOne(Pair<String, String> id) {
        Transaction transaction = transactionMap.get(id);
        if (null == transaction) {
            throw new TransactionNotFoundException(NOT_FOUND_ERROR_MESSAGE);
        }
        return transaction;
    }

    public Map<Pair<String , String>, Transaction> findAll() {
        return transactionMap;
    }
}
