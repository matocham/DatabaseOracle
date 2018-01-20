package pl.edu.pb.wi.project.database.controllers.view;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.pb.wi.project.database.controllers.LoginController;
import pl.edu.pb.wi.project.database.models.view.OfferedMe;
import pl.edu.pb.wi.project.database.repositories.UsersRepository;
import pl.edu.pb.wi.project.database.repositories.view.OfferedMeRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OfferedMeController {

    @Autowired
    OfferedMeRepository offeredMeRepository;

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping(value = "/offered", method = RequestMethod.GET)
    public String offered(Model model, HttpServletRequest request, HttpSession session){
        Long sessionUserId = (Long) session.getAttribute(LoginController.USER_ID_SESSION);//= request.getParameter("login");
        String login = usersRepository.findById(sessionUserId).getLogin();
        List<OfferedMe> myOfferList = offeredMeRepository.findByOwnerLogin(login);
        model.addAttribute("myOfferedMeList", myOfferList);
        return "offered";
    }
}
