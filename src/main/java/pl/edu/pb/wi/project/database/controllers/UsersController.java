package pl.edu.pb.wi.project.database.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.pb.wi.project.database.models.Users;
import pl.edu.pb.wi.project.database.repositories.UsersRepository;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/*Wświetlenie tabeli users na stronie*/
@Controller
public class UsersController {
    @Autowired
    DataSource dataSource;

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping("/usersPrint")
    public String usersPrint(Model model){
        List<Users> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        model.addAttribute("users", users);
        //usersRepository.finalizeExchange(2L);
        return "usersPrint";
    }

    @RequestMapping(value = "/findUserByLogin", method = RequestMethod.GET)
    public String printPremiumUser(Model model, HttpServletRequest req){
        String login = req.getParameter("login");
        List<Users> users;
        users = usersRepository.findByLogin(login);
        model.addAttribute("users", users);
        return "findUserByLogin";
    }

    @RequestMapping(value = "/setUserPasswordByLogin", method = RequestMethod.GET)
    public String setUserPasswordByLogin(Model model, HttpServletRequest request){
        //Users user = new Users();
        //model.addAttribute("user",user);

        String newPassword = request.getParameter("password");
        String login = request.getParameter("login");
       // usersRepository.setPasswordByLogin(newPassword, login);

        return "setUserPasswordByLogin";
    }

    @RequestMapping(value = "/setUserPasswordById", method = RequestMethod.GET)
    public String setUserPasswordById(Model model, HttpServletRequest request){
        //Users user = new Users();
        //model.addAttribute("user",user);

        String newPassword = request.getParameter("password");
        Long id = Long.parseLong(request.getParameter("login"));
        //usersRepository.setPasswordById(newPassword, id);

        return "setUserPasswordById";
    }

    //localhost:8090/userDelete?id=1
    @RequestMapping(value = "/userDelete", method = RequestMethod.GET)
    public String adminUserDelete(Model model, HttpServletRequest request){
        long userId = Long.parseLong(request.getParameter("id"));
        System.out.println("userId: " + userId);
        Users user = usersRepository.findById(userId);
        model.addAttribute("deletedUser", user);
        usersRepository.delete(userId);
        //usersRepository.finalizeExchange(2L);
        return "userDelete";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model , HttpServletRequest request){
        String login = request.getParameter("login");
        String lastName = request.getParameter("last_name");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        if(login != null && login != "") {
            Users user = new Users();
            user.setLogin(login);
            user.setName(name);
            user.setLastName(lastName);
            user.setUserPassword(password); //Problem trzeba wywołać get_hash_val(p_in VARCHAR2) z oracla do hasowania
            user.setEmail(email);
            user.setCity(city);
            usersRepository.save(user);
        }
        return "register";
    }
}
