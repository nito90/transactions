package com.toulios.api;

import com.toulios.model.Account;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * Interface for common CRUD operations
 *
 */
public interface AbstractRespository <T extends Serializable> {

    T add(T item);

    T update(T item);
}
