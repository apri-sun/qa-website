package bekyiu.service.impl;

import bekyiu.domain.Question;
import bekyiu.mapper.QuestionMapper;
import bekyiu.service.IQuestionService;
import bekyiu.service.ISensitiveWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements IQuestionService
{
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ISensitiveWordsService sensitiveWordsService;
    @Override
    public void delete(Long id)
    {
        questionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Question question)
    {
        this.filterText(question);
        questionMapper.insert(question);
    }

    private void filterText(Question question)
    {
        //内容中可能有html标签
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        //敏感词
        question.setTitle(sensitiveWordsService.filter(question.getTitle()));
        question.setContent(sensitiveWordsService.filter(question.getContent()));
    }

    @Override
    @Transactional(readOnly = true)
    public Question get(Long id)
    {
        return questionMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> listAll()
    {
        return questionMapper.selectAll();
    }

    @Override
    public void update(Question question)
    {
        questionMapper.updateByPrimaryKey(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getLatestQuestions(Integer userId, Integer offset, Integer limit)
    {
        return questionMapper.getLatestQuestions(userId, offset, limit);
    }
}
