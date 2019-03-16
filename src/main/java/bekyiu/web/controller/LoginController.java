package bekyiu.web.controller;

import bekyiu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Controller
public class LoginController
{
    @Autowired
    private IUserService userService;

    @RequestMapping("/reg")
    public String register(Model model, HttpServletResponse resp, String username, String password)
    {
        Map<String, String> map = userService.register(username, password);
        //注册后自动登陆, 也要下发ticket
        return sent(map, model, resp);
    }

    @RequestMapping("/loginToHtml")
    public String loginToHtml()
    {

        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletResponse resp, String username, String password,
    @RequestParam(value = "remember_me", defaultValue = "false") boolean rememberMe)
    {
        Map<String, String> map = userService.login(username, password);
        //包含说明登陆成功
        return sent(map, model, resp);
    }

    @RequestMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket)
    {
        userService.logout(ticket);
        return "redirect:/";
    }

    private String sent(@NotNull Map<String, String> map, Model model, HttpServletResponse resp)
    {
        if(map.containsKey("ticket"))
        {
            //下发ticket
            Cookie cookie = new Cookie("ticket", map.get("ticket"));
            cookie.setPath("/");
            resp.addCookie(cookie);
            return "redirect:/";
        }
        else
        {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }
}
