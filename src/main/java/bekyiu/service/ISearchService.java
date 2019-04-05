package bekyiu.service;

import bekyiu.domain.Question;

import java.util.List;

public interface ISearchService
{
    List<Question> searchQuestion(String keyword, int offset, int count,
                                  String hlPre, String hlPos) throws Exception;

    //每发布一个问题就要更新索引
    boolean indexQuestion(Long questionId, String title, String content) throws Exception;
}
