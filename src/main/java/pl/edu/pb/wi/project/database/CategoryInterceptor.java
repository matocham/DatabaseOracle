package pl.edu.pb.wi.project.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pb.wi.project.database.models.Category;
import pl.edu.pb.wi.project.database.repositories.CategoryRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CategoryInterceptor implements HandlerInterceptor {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
bh
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String path = request.getServletPath();
        if (path == null) {
            return;
        }
        if (true) { // when categories should be shown
            Iterable<Category> categories;
            if (path.equals("/category")) {
                String parentCategory = request.getParameter("id");
                Long parentId = Long.valueOf(parentCategory);
                categories = categoryRepository.findByParentCategoryId(parentId);
            } else {
                categories = categoryRepository.findByParentCategoryId(null);
            }
            request.setAttribute("categories", categories);
        }
    }

        @Override
        public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        }
    }
