package bekyiu.mapper;

import bekyiu.domain.Question;
import org.apache.ibatis.annotations.Param;
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

    /**
     *
     * @param userId 当userId不等等于0的时候, 会把userId作为过滤条件, 仅仅查看这个用户的问题
     * @param offset
     * @param limit
     * @return
     */
    List<Question> getLatestQuestions(@Param("userId") Integer userId,
                                      @Param("offset") Integer offset,
                                      @Param("limit") Integer limit);
}