package bekyiu.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MessageVO
{
    private Long id;

    private Long fromId;

    private Long toId;

    private Date createdDate;

    private Integer hasRead;

    private String conversationId;

    private String content;

    private Integer count;

    public String getConversationId()
    {
        if(fromId < toId)
        {
            return fromId + "_" + toId;
        }
        else
        {
            return toId + "_" + fromId;
        }
    }
}
