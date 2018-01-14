package pl.edu.pb.wi.project.database.controllers;

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
public class UsersAdminController {
    @Autowired
    DataSource dataSource;

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping("/usersAdmin")
    public String adminUser(Model model){
        List<Users> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        model.addAttribute("users", users);
        usersRepository.finalizeExchange(2L);
        return "usersAdmin";
    }

    //localhost:8090/userDelete?id=1
    @RequestMapping(value = "/userDelete", method = RequestMethod.GET)
    public String adminUserDelete(Model model, HttpServletRequest request){
        long userId = Long.parseLong(request.getParameter("id"));
        System.out.println("userId: " + userId);
        Users user = usersRepository.findById(userId);
        model.addAttribute("deletedUser", user);
        usersRepository.delete(userId);
        usersRepository.finalizeExchange(2L);
        return "userDelete";
    }
}
