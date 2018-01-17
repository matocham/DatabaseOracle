package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.repositories.OfferRepository;
import pl.edu.pb.wi.project.database.repositories.ProductRepository;

import javax.servlet.http.HttpSession;

/**
 * Created by Dominik on 16.01.2018.
 */
@Controller
@SessionAttributes("USER_ID_SESSION")
public class OfferController {
    @Autowired
    ProductRepository productRepository;
    public static final String USER_ID_SESSION = "userId";
        @RequestMapping("/addOffer")
        String index(Model model,HttpSession session) {

             Iterable<Product> myProduct = productRepository.findProductByOwnerId( Long.parseLong(session.getAttribute(USER_ID_SESSION).toString()));
             
             model.addAttribute("myProducts", myProduct);
            return "addOffer";
        }
}
