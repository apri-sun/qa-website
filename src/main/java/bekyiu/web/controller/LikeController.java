package bekyiu.web.controller;

import bekyiu.domain.HostHolder;
import bekyiu.service.ILikeService;
import bekyiu.util.EntityType;
import bekyiu.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController
{
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private ILikeService likeService;

    @RequestMapping("/like")
    @ResponseBody
    public String like(Long commentId)
    {
        if(hostHolder.getUser() == null || hostHolder.getUser().getUsername() == null)
        {
            return JsonUtil.getJsonString(1);
        }
        likeService.addLike(hostHolder.getUser().getId(), commentId, EntityType.ENTITY_COMMENT);
        return JsonUtil.getJsonString(0, String.valueOf(likeService.getLikeCount(commentId, EntityType.ENTITY_COMMENT)));
    }

    @RequestMapping("/dislike")
    @ResponseBody
    public String dislike(Long commentId)
    {
        if(hostHolder.getUser() == null || hostHolder.getUser().getUsername() == null)
        {
            return JsonUtil.getJsonString(1);
        }
        likeService.addDislike(hostHolder.getUser().getId(), commentId, EntityType.ENTITY_COMMENT);
        return JsonUtil.getJsonString(0, String.valueOf(likeService.getLikeCount(commentId, EntityType.ENTITY_COMMENT)));
    }
}
