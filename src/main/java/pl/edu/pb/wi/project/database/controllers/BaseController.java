package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pb.wi.project.database.models.Offer;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.repositories.OfferRepository;
import pl.edu.pb.wi.project.database.repositories.ProductRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BaseController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferRepository offerRepository;

    @RequestMapping("/")
    String index(Model model) {
        Iterable<Product> productsFromCategory = productRepository.findByExchangedFalseOrderByAddDateDesc();
        model.addAttribute("products", productsFromCategory);
        return "index";
    }

    @RequestMapping("/category")
    String category(@RequestParam(name = "id") Long category, Model model) {
        Iterable<Product> productsFromCategory = productRepository.findByCategoryTree(category);
        model.addAttribute("products", productsFromCategory);
        return "category";
    }

    @RequestMapping("/product/{id}")
    String showProduct(@PathVariable(name = "id") Long id, Model model) {
        Product product = productRepository.findOne(id);
        if (product == null) {
            return "redirect:/index";
        }
        model.addAttribute("product", product);
        return "showProduct";
    }

    @RequestMapping("/offer/{id}")
    String showOffer(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
        Offer offer = offerRepository.findOne(id);
        if (offer == null) {
            return "redirect:/index";
        }
        Long sessionUserId = (Long) session.getAttribute(LoginController.USER_ID_SESSION);
        model.addAttribute("offer", offer);
        if (sessionUserId == null) {
            return "redirect:/login";
        }
        if (offer.getBuyer().getId().equals(sessionUserId)) {
            return "showMyOffer";
        } else if (offer.getOfferedProducts().get(0).getOwner().getId().equals(sessionUserId)) {
            return "showIncomingOffer";
        }
        return "redirect:/index";
    }
}
