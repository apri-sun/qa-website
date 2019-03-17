package bekyiu.web.interceptor;

import bekyiu.domain.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginRequiredInterceptor extends HandlerInterceptorAdapter
{
    @Autowired
    private HostHolder hostHolder;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if(hostHolder.getUser() == null || hostHolder.getUser().getId() == null)
        {
            response.sendRedirect("/loginToHtml?next=" + request.getRequestURI());
            return false;
        }
        return true;
    }
}
