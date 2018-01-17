package pl.edu.pb.wi.project.database.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.pb.wi.project.database.models.Users;
import pl.edu.pb.wi.project.database.repositories.UsersRepository;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/*Wświetlenie tabeli users na stronie*/
@Controller
public class UsersController {
    @PersistenceContext
    EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping("/usersPrint")
    public String usersPrint(Model model){
        List<Users> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        model.addAttribute("users", users);
        return "usersPrint";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model , HttpServletRequest request){
        String login = request.getParameter("login");
        String lastName = request.getParameter("last_name");
        String name = request.getParameter("name");
        String password = request.getParameter("user_password");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        if(login != null && login != "") {
            if(login != null && login != "") {
                /*
                String hash = (String) em
                        .createNativeQuery(
                                "SELECT utilities.get_hash_val(:p_in) FROM DUAL"
                        )
                        .setParameter("p_in", password)
                        .getSingleResult();
                        */
                Users user = new Users();
                user.setLogin(login);
                user.setName(name);
                user.setLastName(lastName);
                user.setUserPassword(password); //Problem trzeba wywołać get_hash_val(p_in VARCHAR2) z oracla do hasowania
                user.setEmail(email);
                user.setCity(city);
                usersRepository.save(user);
            }
        }
        return "register";
    }
}
