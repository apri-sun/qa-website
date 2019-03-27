package bekyiu.async;

import java.util.HashMap;
import java.util.Map;

//事件发生的现场
public class EventModel
{
    //触发什么类型的事件
    private EventType type;
    //触发者
    private Long actorId;
    //触发的载体
    private Integer entityType;
    private Long entityId;
    //和触发载体相关联的人
    private Long entityOwnerId;
    //一些可能要扩展的字段 类似ViewObject
    private Map<String, String> exts = new HashMap<>();


    public String getExts(String key)
    {
        return exts.get(key);
    }

    public EventModel setExts(String key, String value)
    {
        exts.put(key, value);
        return this;
    }

    public EventModel(){}

    public EventType getType()
    {
        return type;
    }

    public EventModel setType(EventType type)
    {
        this.type = type;
        return this;
    }

    public Long getActorId()
    {
        return actorId;
    }

    public EventModel setActorId(Long actorId)
    {
        this.actorId = actorId;
        return this;
    }

    public Integer getEntityType()
    {
        return entityType;
    }

    public EventModel setEntityType(Integer entityType)
    {
        this.entityType = entityType;
        return this;
    }

    public Long getEntityId()
    {
        return entityId;
    }

    public EventModel setEntityId(Long entityId)
    {
        this.entityId = entityId;
        return this;
    }

    public Long getEntityOwnerId()
    {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(Long entityOwnerId)
    {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts()
    {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts)
    {
        this.exts = exts;
        return this;
    }
}
