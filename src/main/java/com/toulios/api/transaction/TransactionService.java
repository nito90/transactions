package com.toulios.api.transaction;

import com.toulios.model.Transaction;
import javafx.util.Pair;

import java.util.Map;

public interface TransactionService {

    Transaction addTransaction(Transaction item);

    void deleteTransaction(Pair<String, String> key);

    Transaction findTransaction(Pair<String, String> id);

    Map<Pair<String , String>, Transaction> findAll();
}
