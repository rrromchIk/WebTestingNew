package com.epam.testing.model.entity;

import java.io.Serializable;

/**
 * Abstract entity class
 *
 * @author rom4ik
 */

public class Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
