package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pb.wi.project.database.models.Users;
import pl.edu.pb.wi.project.database.repositories.UsersRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    public static final String USER_SESSION = "user";
    public static final String USER_ID_SESSION = "userId";
    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/login")
    String login(){
        return "login";
    }

    @PostMapping("/login")
    String authLogin(@RequestParam() String username, @RequestParam String password, HttpSession session){
        List<Users> matchedUsers = usersRepository.findUsersByLoginAndPassword(username, password);
        if(matchedUsers.isEmpty()){
            return "login";
        }
        session.setAttribute(USER_SESSION, matchedUsers.get(0));
        session.setAttribute(USER_ID_SESSION, matchedUsers.get(0).getId());
        return "redirect:/";
    }

    @GetMapping("/logout")
    String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
