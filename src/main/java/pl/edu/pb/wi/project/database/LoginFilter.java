package pl.edu.pb.wi.project.database;

import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import pl.edu.pb.wi.project.database.controllers.LoginController;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestFacade trueRequest = (RequestFacade) request;
        ResponseFacade trueResponse = (ResponseFacade) response;
        HttpSession currentSession = trueRequest.getSession(false);
        String requestPath = trueRequest.getServletPath();

        if(requestPath.startsWith("/css/") || requestPath.startsWith("/js/") || requestPath.startsWith("/image/") || requestPath.startsWith("/webjars/")){
            chain.doFilter(request, response);
            return;
        }

        if (currentSession != null) {
            Long userId = (Long) currentSession.getAttribute(LoginController.USER_ID_SESSION);
            if (userId == null && !isAllowedPathWithoutLogin(requestPath)) {
                trueResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                trueResponse.getWriter().write("<h1>Access is Forbidden!</h1>");
                return;
            }
            if (userId != null && isForbiddenAfterLogin(requestPath)) {
                trueResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                trueResponse.getWriter().write("<h1>Page not found!</h1>");
                return;
            }
        } else if(!isAllowedPathWithoutLogin(requestPath)){
            trueResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            trueResponse.getWriter().write("<h1>Access is Forbidden!</h1>");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private boolean isAllowedPathWithoutLogin(String path) {
        List<String> allowedPaths = new ArrayList<>();
        allowedPaths.add("/login");
        allowedPaths.add("/register");
        allowedPaths.add("/newOffer");
        for (String authPath : allowedPaths) {
            if (path.contains(authPath)) {
                return true;
            }
        }
        if (path.equals("/")) {
            return true;
        }
        return false;
    }

    private boolean isForbiddenAfterLogin(String path) {
        List<String> allowedPaths = new ArrayList<>();
        allowedPaths.add("/login");
        allowedPaths.add("/register");
        for (String authPath : allowedPaths) {
            if (path.contains(authPath)) {
                return true;
            }
        }
        return false;
    }
}
