package bekyiu.controller;

import bekyiu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class LoginController
{
    @Autowired
    private IUserService userService;

    @RequestMapping("/reg")
    public String register(Model model, String username, String password)
    {
        Map<String, String> map = userService.register(username, password);
        if(map.size() != 0)
        {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
        return "redirect:/";
    }

    @RequestMapping("/loginToHtml")
    public String loginToHtml()
    {

        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model, String username, String password)
    {
        Map<String, String> map = userService.login(username, password);
        if(map.size() != 0)
        {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
        return "redirect:/";
    }
}
