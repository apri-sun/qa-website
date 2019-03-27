package bekyiu.async;

import java.util.List;

/*
定义规范, 所有处理事件的处理器必须实现当前接口
 */
public interface EventHandler
{
    //处理函数
    void doHandle(EventModel eventModel);

    //当前这个handler, 对哪些类型的事件感兴趣
    //也就是说当前handler需要处理这些类型的eventModel
    //many to many
    List<EventType>  getSupportEventTypes();
}
