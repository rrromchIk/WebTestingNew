package com.epam.testing.model.dao;

import com.epam.testing.model.entity.Entity;
import java.util.List;

/**
 * Main CRUD DAO interface.
 *
 * @author rom4ik
 */

public interface DAO<T extends Entity> {
    int getAmountOfRecords();
    List<T> getAll(int limit, int offset);
    T getById(long id);
    long create(T model);
    boolean update(T model);
    boolean delete(long id);
}
