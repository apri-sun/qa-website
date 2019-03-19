package bekyiu.service.impl;

import bekyiu.domain.Comment;
import bekyiu.mapper.CommentMapper;
import bekyiu.service.ICommentService;
import bekyiu.service.ISensitiveWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
@Service
@Transactional
public class CommentServiceImpl implements ICommentService
{
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ISensitiveWordsService sensitiveWordsService;
    @Override
    public void update(Comment comment)
    {
        this.filterText(comment);
        commentMapper.updateByPrimaryKey(comment);
    }

    @Override
    public void delete(Long id)
    {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Comment comment)
    {
        this.filterText(comment);
        commentMapper.insert(comment);
    }

    private void filterText(Comment comment)
    {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveWordsService.filter(comment.getContent()));
    }

    @Override
    @Transactional(readOnly = true)
    public Comment get(Long id)
    {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> listAll()
    {
        return commentMapper.selectAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> selectCommentByEntity(Integer entityType, Long entityId)
    {
        return commentMapper.selectCommentByEntity(entityType, entityId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCommentCount(Integer entityType, Long entityId)
    {
        return commentMapper.getCommentCount(entityType, entityId);
    }
}
