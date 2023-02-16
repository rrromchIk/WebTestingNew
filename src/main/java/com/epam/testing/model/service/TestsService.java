package com.epam.testing.model.service;

import com.epam.testing.model.dao.TestDAO;
import com.epam.testing.model.dao.impl.TestDAOImpl;
import com.epam.testing.model.entity.test.Test;

import java.util.List;

public class TestsService {
    private final TestDAO dao;

    public TestsService() {
        this.dao = new TestDAOImpl();
    }

    public TestsService(TestDAO dao) {
        this.dao = dao;
    }

    public Test getTestById(long testId) {
        return dao.getById(testId);
    }

    public int getAmountOfTests() {
        return dao.getAmountOfRecords();
    }

    public int getAmountOfTestsOnParticularSubject(String subject) {
        return dao.getAmountOnParticularSubject(subject);
    }

    public List<Test> getAllTests(int limit, int offset) {
        return dao.getAll(limit, offset);
    }

    public List<Test> getTestsSortedByNumberOfQuestions(int limit, int offset) {
        return dao.getAllSortedByNumberOfQuestions(limit, offset);
    }

    public List<Test> getTestsSortedByName(int limit, int offset) {
        return dao.getAllSortedByName(limit, offset);
    }

    public List<Test> getTestsSortedByDifficulty(int limit, int offset) {
        return dao.getAllSortedByDifficulty(limit, offset);
    }

    public List<Test> getTestsOnParticularSubject(String subject, int limit, int offset) {
        return dao.getAllOnParticularSubject(subject, limit, offset);
    }

    public List<String> getAllSubjects() {
        return dao.getAllTestsSubjects();
    }

    public boolean deleteTest(long testId) {
        return dao.delete(testId);
    }

    public boolean createTest(Test test) {
        long id = dao.create(test);
        test.setId(id);
        return id != -1;
    }

    public boolean updateTest(Test test) {
        return dao.update(test);
    }
}
