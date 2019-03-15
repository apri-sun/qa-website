package bekyiu.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Question
{
    private Long id;

    private String title;

    private Long userId;

    private Date createDate;

    private Integer commentCount;

    private String content;

}