package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pb.wi.project.database.models.Category;
import pl.edu.pb.wi.project.database.models.Offer;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.repositories.CategoryRepository;
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
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OfferRepository offerRepository;
    public static final String USER_ID_SESSION = "userId";

    //@RequestMapping("/addOffer")
    //String index() {
   //     return "addOffer";
   // }


    @RequestMapping("/myProducts")
    String getMyProducts(Model model, HttpSession session) {
        Iterable<Product> myProducts = productRepository.findProductByOwnerId(Long.parseLong(session.getAttribute(USER_ID_SESSION).toString()));
        model.addAttribute("myProducts", myProducts);
        return "myProducts";
    }


    @RequestMapping("/addOffer")
    String addOffer(@RequestParam(name = "id") Long productId, Model model,HttpSession session) {
        Iterable<Product> myProducts = productRepository.findProductByOwnerId(Long.parseLong(session.getAttribute(USER_ID_SESSION).toString()));
        Product productFromOffer=  productRepository.findById(productId);
        Iterable<Category> categories= categoryRepository.findAll();
        model.addAttribute("myProducts", myProducts);
        model.addAttribute("category",categories);
        model.addAttribute("productFromOffer",productFromOffer);
        return "addOffer";
    }
    @PostMapping("/addOffer")
    String addOffer(Model model,@RequestParam("checkboxname")String[] checkboxvalues){

            ModelAndView mv = new ModelAndView("functionList");
           // mv.addObject("functionList", getFunctionsFromDB());
        return "index";
    }
}
