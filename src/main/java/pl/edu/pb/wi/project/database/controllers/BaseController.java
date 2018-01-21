package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pb.wi.project.database.models.Category;
import pl.edu.pb.wi.project.database.models.Offer;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.models.Users;
import pl.edu.pb.wi.project.database.repositories.CategoryRepository;
import pl.edu.pb.wi.project.database.repositories.OfferRepository;
import pl.edu.pb.wi.project.database.repositories.ProductRepository;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class BaseController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Value("${fileUpload.baseLocation}")
    private String userFilesPath;

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
        } else if (offer.getProduct().getOwner().getId().equals(sessionUserId)) {
            return "showIncomingOffer";
        }
        return "redirect:/";
    }

    @PostMapping("/offer/{id}/accept")
    String acceptOffer(@PathVariable(name = "id") Long id, HttpSession session) {
        Offer offer = offerRepository.findOne(id);
        if (offer == null) {
            return "redirect:/";
        }
        Long sessionUserId = (Long) session.getAttribute(LoginController.USER_ID_SESSION);

        if (sessionUserId == null) {
            return "redirect:/login";
        }
        if (offer.getBuyer().getId().equals(sessionUserId)) {
            return "redirect:/";
        } else if (offer.getProduct().getOwner().getId().equals(sessionUserId)) {
            offerRepository.finalizeExchange(id);
            return "offerAccepted";
        }
        return "redirect:/";
    }

    @GetMapping("/addProduct")
    String addProduct(Model model) {
        List<Category> allCategories = new ArrayList<>();
        categoryRepository.findAll().forEach(allCategories::add);
        Map<Long, String> categoriesMap = new TreeMap<>();
        for (Category cat : allCategories) {
            String catTree = buildCatTree(cat);
            catTree = catTree.trim().substring(3);
            categoriesMap.put(cat.getId(), catTree);
        }
        model.addAttribute("categoriesTree", categoriesMap);
        return "addProduct";
    }

    String buildCatTree(Category category) {
        if (category == null) {
            return "";
        }
        return buildCatTree(category.getParentCategory()) + " -> " + category.getName();
    }

    @PostMapping("/addProduct")
    String processProduct(@RequestParam() String title, @RequestParam() String description,
                          @RequestParam() Long leftCategory, @RequestParam() Long resultCategory,
                          @RequestParam() MultipartFile image, HttpSession session) throws IOException {
        String imageContainer = UUID.randomUUID().toString();
        String imagePath = userFilesPath + "images/product/" + imageContainer;
        File newDir = new File(imagePath);
        newDir.mkdirs();
        File imageFile = new File(imagePath, image.getOriginalFilename());
        FileOutputStream fileSaveStream = new FileOutputStream(imageFile);
        fileSaveStream.write(image.getBytes());
        fileSaveStream.close();

        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setExchanged(false);
        product.setCategory(categoryRepository.findOne(leftCategory));
        product.setExchangeFor(categoryRepository.findOne(resultCategory));
        product.setOwner((Users) session.getAttribute(LoginController.USER_SESSION));
        product.setImageUrl("images/product/" + imageContainer + "/" + image.getOriginalFilename());
        productRepository.save(product);

        return "redirect:/myProducts";
    }
}
