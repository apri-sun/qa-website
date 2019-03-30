package bekyiu.domain;

import lombok.Data;

import java.util.Date;

//feed是将用户主动订阅的若干消息源组合在一起形成内容聚合器，帮助用户持续地获取最新的订阅源内容。
//feed流即持续更新并呈现给用户内容的信息流。
//新鲜事
@Data
public class Feed
{
    private Long id;

    //不同的新鲜事有不同的类型, 譬如xx关注了xx, xx回复了xx问题
    private Integer type;

    //新鲜事一定是由某个人来触发的
    private Long userId;

    //触发时间
    private Date createdDate;

    //新鲜事的具体内容 是一个json串
    private String data;
}