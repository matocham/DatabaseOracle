package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.project.database.models.Offer;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.models.Users;
import pl.edu.pb.wi.project.database.repositories.OfferRepository;
import pl.edu.pb.wi.project.database.repositories.ProductRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 16.01.2018.
 */
@Controller
@SessionAttributes("USER_ID_SESSION")
public class OfferController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferRepository offerRepository;


    //@RequestMapping("/addOffer")
    //String index() {
    //     return "addOffer";
    // }


    @RequestMapping("/myProducts")
    String getMyProducts(Model model, HttpSession session) {
        Iterable<Product> myProducts = productRepository.findProductByOwnerId(Long.parseLong(session.getAttribute(LoginController.USER_ID_SESSION).toString()));
        model.addAttribute("myProducts", myProducts);
        return "myProducts";
    }

    @GetMapping("/addOffer/{prodId}")
    String addOffer(Model model, @PathVariable("prodId") Long productId, HttpSession session) {
        Product wantedProduct = productRepository.findOne(productId);
        if (wantedProduct == null) {
            return "redirect:/";
        }
        Iterable<Product> myProducts = productRepository.findByCategoryTreeAndOwner(wantedProduct.getExchangeFor().getId(), (Long) session.getAttribute(LoginController.USER_ID_SESSION));
        model.addAttribute("myProducts", myProducts);
        model.addAttribute("wantedProduct", wantedProduct);
        return "addOffer";
    }

    @PostMapping("/addOffer")
    String addOfferProcessor(@RequestParam() Long wantedProductId, @RequestParam("prodCheckbox") List<Long> selectedCheck, HttpSession session) {
        if (selectedCheck.isEmpty()) {
            return "redirect:/";
        }
        List<Product> selectedProducts = new ArrayList<>();
        productRepository.findAll(selectedCheck).forEach(prod -> {
            if (!prod.getExchanged()) {
                selectedProducts.add(prod);
            }
        });
        if (selectedProducts.isEmpty()) {
            return "redirect:/";
        }
        Offer offer = new Offer();
        offer.setBuyer((Users) session.getAttribute(LoginController.USER_SESSION));
        offer.setProduct(productRepository.findOne(wantedProductId));
        offer.setOfferedProducts(selectedProducts);
        offer.setRate(-1);
        offerRepository.save(offer);
        return "redirect:/";
    }
}
