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
    DataSource dataSource;

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        System.out.println("DATASOURCE = " + dataSource);
        model.addAttribute("allUsers", usersRepository.findAll());
        model.addAttribute("foundUser", usersRepository.findByEmail("bbrzozowski@examle.com"));
        usersRepository.finalizeExchange(2L);
        return "greeting";
    }

}
