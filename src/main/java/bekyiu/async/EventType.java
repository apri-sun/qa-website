package bekyiu.async;
import lombok.Getter;

//往队列中塞的事件的类型
public enum EventType
{
    LIKE(0), COMMENT(1), LOGIN(2), MAIL(3), FOLLOW(4), UNFOLLOW(5),
    FOLLOW_QUESTION(6), UNFOLLOW_QUESTION(7), ADD_QUESTION(8);

    @Getter
    private Integer value;

    EventType(Integer value)
    {
        this.value = value;
    }
}
