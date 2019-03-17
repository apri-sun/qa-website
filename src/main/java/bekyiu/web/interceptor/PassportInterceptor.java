package bekyiu.web.interceptor;

import bekyiu.domain.HostHolder;
import bekyiu.domain.LoginTicket;
import bekyiu.domain.User;
import bekyiu.service.ILoginTicketService;
import bekyiu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor
{
    @Autowired
    private ILoginTicketService ticketService;
    @Autowired
    private IUserService userService;
    @Autowired
    private HostHolder hostHolder;

    @Override
    //一律都返回true, 因为即便不登录, 也可以浏览信息
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception
    {
        LoginTicket loginTicket = null;
        Cookie[] cookies = req.getCookies();
        if(cookies == null)
        {
            //以免在页面获取报空指针
            hostHolder.setUser(new User());
        }
        else
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName().equals("ticket"))
                {
                    String ticket = cookie.getValue();
                    loginTicket = ticketService.selectByTicket(ticket);
                    break;
                }
            }

            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0)
            {
                //以免在页面获取报空指针
                hostHolder.setUser(new User());
                return true;
            }
            User user = userService.get(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object o, ModelAndView modelAndView) throws Exception
    {
        if (modelAndView != null)
        {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object o, Exception e) throws Exception
    {
        hostHolder.clear();
    }
}
