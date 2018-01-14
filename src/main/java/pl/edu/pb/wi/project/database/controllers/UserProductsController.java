package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.repositories.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserProductsController {
    @Autowired
    DataSource dataSource;

    @Autowired
    ProductRepository productRepository;

    //localhost:8090/userProducts
    @RequestMapping(value = "/userProducts", method = RequestMethod.GET)
    public String userProducts(Model model, HttpServletRequest request){
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        model.addAttribute("products", products);
        return "userProducts";
    }

}
