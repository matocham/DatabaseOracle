package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.repositories.ProductRepository;

@Controller
public class BaseController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("/category")
    String category(@RequestParam(name = "id") Long category, Model model) {
        Iterable<Product> productsFromCategory = productRepository.findByCategoryTree(category);
        model.addAttribute("products", productsFromCategory);
        return "category";
    }
}
