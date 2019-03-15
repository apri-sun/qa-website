package bekyiu.mapper;

import bekyiu.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(Question question);

    Question selectByPrimaryKey(Long id);

    List<Question> selectAll();

    int updateByPrimaryKey(Question question);
}