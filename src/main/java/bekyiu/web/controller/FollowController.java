package bekyiu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FollowController
{
    @RequestMapping("/followUser")
    @ResponseBody
    public String followUser(Long userId)
    {
        return "sb";
    }
}
