package com.epam.testing.controller;

/**
 * Dispatch info class. Provides possibility in different situations
 * to make forward or redirect
 *
 * @author rom4ik
 */
public class DispatchInfo {
    private boolean redirect;
    private String page;

    /**
     * Constructor for creating instance
     *
     * @param redirect initializes redirect state
     * @param page initializes redirect state
     */
    public DispatchInfo(boolean redirect, String page) {
        this.redirect = redirect;
        this.page = page;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public String getPage() {
        return page;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
