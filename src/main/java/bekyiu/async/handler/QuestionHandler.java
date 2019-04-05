package bekyiu.async.handler;

import bekyiu.async.EventHandler;
import bekyiu.async.EventModel;
import bekyiu.async.EventType;
import bekyiu.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class QuestionHandler implements EventHandler
{
    @Autowired
    private ISearchService searchService;
    @Override
    public void doHandle(EventModel eventModel)
    {
        try
        {
            //更新索引库
            searchService.indexQuestion(eventModel.getEntityId(),
                    eventModel.getExts("title"),
                    eventModel.getExts("content"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
