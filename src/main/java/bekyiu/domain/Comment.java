package bekyiu.domain;

import lombok.Data;

import java.util.Date;
@Data
public class Comment
{
    private Long id;

    private Long entityId;

    private Integer entityType;

    private Date createdDate;

    private Long userId;

    private String content;
    //用于控制评论是否被删除
    //private Integer status;
}