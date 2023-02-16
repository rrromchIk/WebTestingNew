package com.epam.testing.model.dao;

import com.epam.testing.model.entity.test.Test;
import java.util.List;

/** Test DAO interface
 *
 * @author rom4ik
 */

public interface TestDAO extends DAO<Test> {
    Test getByName(String name);
    List<Test> getAllSortedByNumberOfQuestions(int limit, int offset);
    List<Test> getAllSortedByName(int limit, int offset);
    List<Test> getAllSortedByDifficulty(int limit, int offset);
    List<Test> getAllOnParticularSubject(String subject, int limit, int offset);
    List<String> getAllTestsSubjects();
    int getAmountOnParticularSubject(String subject);
}
