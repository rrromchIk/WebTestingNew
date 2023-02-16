package com.epam.testing.model.dao;

import com.epam.testing.model.entity.question.Question;
import java.util.List;

/** Question DAO interface
 *
 * @author rom4ik
 */

public interface QuestionDAO {
    int getAmountOfRecordsByTestId(long testId);
    List<Question> getAllByTestId(long id);
    long create(long testId, Question question);
    boolean delete(long id);
}
