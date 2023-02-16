package com.epam.testing.model.dao;

import java.util.List;

/** Question answer DAO interface
 *
 * @author rom4ik
 */

public interface QuestionAnswerDAO {
    List<String> getAllByQuestionId(long id);
    boolean create(long id, String text);
}
