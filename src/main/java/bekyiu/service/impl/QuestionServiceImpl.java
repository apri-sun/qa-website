package bekyiu.service.impl;

import bekyiu.domain.Question;
import bekyiu.mapper.QuestionMapper;
import bekyiu.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements IQuestionService
{
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public void delete(Long id)
    {
        questionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Question question)
    {
        questionMapper.insert(question);
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
}
