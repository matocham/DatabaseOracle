package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pb.wi.project.database.repositories.UsersRepository;

import javax.sql.DataSource;

@Controller
public class GreetingController {
    @Autowired
    UsersRepository usersRepository;

    @RequestMapping("/login")
    public String greeting() {
        return "login";
    }

    @RequestMapping("/category")
    public String greeting2() {
        return "category";
    }
}
