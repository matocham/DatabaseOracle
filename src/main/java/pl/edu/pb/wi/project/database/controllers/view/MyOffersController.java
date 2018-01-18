package pl.edu.pb.wi.project.database.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.pb.wi.project.database.models.view.MyOffers;
import pl.edu.pb.wi.project.database.repositories.view.MyOffersRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MyOffersController {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MyOffersRepository myOffersRepository;

    @RequestMapping(value = "/userOffers", method = RequestMethod.GET)
    String userOffers(Model model, HttpServletRequest request){
        String login = request.getParameter("login");
        List<MyOffers> myOfferList = myOffersRepository.findByOwnerLogin(login);
        model.addAttribute("myOfferList", myOfferList);
        return "userOffers";
    }
}
