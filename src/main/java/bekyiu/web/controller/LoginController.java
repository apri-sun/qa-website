package bekyiu.web.controller;

import bekyiu.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Controller
public class LoginController
{
    @Autowired
    private IUserService userService;

    @RequestMapping("/reg")
    public String register(Model model, HttpServletResponse resp,
                           @RequestParam("account") String username, String password,
                           @RequestParam(required = false) String next)
    {
        Map<String, String> map = userService.register(username, password);
        //注册后自动登陆, 也要下发ticket
        return sent(map, model, resp, next);
    }

    @RequestMapping("/loginToHtml")
    public String loginToHtml(Model model, @RequestParam(required = false) String next)
    {
        model.addAttribute("next", next);
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletResponse resp,
                        @RequestParam("account") String username, String password,
                        @RequestParam(value = "remember_me", defaultValue = "false") boolean rememberMe,
                        @RequestParam(required = false) String next)
    {
        Map<String, String> map = userService.login(username, password);
        //包含说明登陆成功
        return sent(map, model, resp, next);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest req)
    {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if ("ticket".equals(cookie.getName()))
                {
                    userService.logout(cookie.getValue());
                    break;
                }
            }
        }
        return "redirect:/";
    }

    private String sent(@NotNull Map<String, String> map, Model model, HttpServletResponse resp, String next)
    {
        if (map.containsKey("ticket"))
        {
            //下发ticket
            Cookie cookie = new Cookie("ticket", map.get("ticket"));
            cookie.setPath("/");
            cookie.setMaxAge(3600 * 24 * 30);
            resp.addCookie(cookie);
            if(StringUtils.isNotBlank(next))
            {
                return "redirect:" + next;
            }
            return "redirect:/";
        }
        else
        {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }
}
