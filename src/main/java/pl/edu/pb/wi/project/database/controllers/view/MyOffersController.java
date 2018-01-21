package pl.edu.pb.wi.project.database.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.pb.wi.project.database.controllers.LoginController;
import pl.edu.pb.wi.project.database.models.Users;
import pl.edu.pb.wi.project.database.models.view.MyOffers;
import pl.edu.pb.wi.project.database.repositories.UsersRepository;
import pl.edu.pb.wi.project.database.repositories.view.MyOffersRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MyOffersController {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MyOffersRepository myOffersRepository;

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping(value = "/userOffers", method = RequestMethod.GET)
    String userOffers(Model model, HttpServletRequest request, HttpSession session){
        Long sessionUserId = (Long) session.getAttribute(LoginController.USER_ID_SESSION);//= request.getParameter("login");
        String login = usersRepository.findById(sessionUserId).getLogin();
        List<MyOffers> myOfferList = myOffersRepository.findByBuyerLogin(login);
        model.addAttribute("myOfferList", myOfferList);
        return "userOffers";
    }
}
