package bekyiu.service;

import bekyiu.domain.Question;

import java.util.List;

public interface IQuestionService
{
    void delete(Long id);

    void save(Question question);

    Question get(Long id);

    List<Question> listAll();

    void update(Question question);

    List<Question> getLatestQuestions(Integer userId, Integer offset, Integer limit);

    Integer updateCommentCount(Long id, Integer commentCount);
}
