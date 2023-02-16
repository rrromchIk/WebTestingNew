package com.epam.testing.util;

/**
 * PaginationService util. Calculates parameters
 * for pagination
 *
 * @author rom4ik
 */
public class PaginationService {
    /**
     * Don't let anyone instantiate this class.
     */
    private PaginationService() {}

    /**
     * @param limit represents amount of records per page
     * @param totalNumber represents total number of records
     * @return total number of pages
     */
    public static Integer getNumberOfPages(Integer limit, Integer totalNumber) {
        return (int)Math.ceil((double)totalNumber / limit);
    }

    /**
     * @param limit represents amount of records per page
     * @param page represents current page
     * @return offset that will be used to get records from database
     */
    public static Integer getOffsetOnCertainPage(Integer limit, Integer page) {
        return limit * (page - 1);
    }

    /**
     * @param page page represents that has to be checked in terms of validness
     * @param totalNumber represents total number of records
     * @param limit represents amount of records per page
     * @return valid page number
     */
    public static int getValidPageNumber(String page, Integer totalNumber, Integer limit) {
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            pageNumber = 1;
        }
        if(pageNumber < 1 || pageNumber > getNumberOfPages(limit, totalNumber)) {
            pageNumber = 1;
        }
        return pageNumber;
    }
}
