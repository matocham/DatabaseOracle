package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pb.wi.project.database.models.ConversationView;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.models.Users;
import pl.edu.pb.wi.project.database.repositories.ConversationViewRepository;
import pl.edu.pb.wi.project.database.repositories.ProductRepository;
import pl.edu.pb.wi.project.database.repositories.UsersRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ConversationControler {
    public static final String USER_ID_SESSION = "userId";
    @Autowired
    ConversationViewRepository conversationViewRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UsersRepository usersRepository;
    @RequestMapping("/conversation")
    String getConversation(@RequestParam(name = "id") Long productId, Model model, HttpSession session){
        Long receiverId=Long.parseLong(session.getAttribute(USER_ID_SESSION).toString());
        Product product= productRepository.findById(productId);
        Users user= usersRepository.findById(receiverId);

        List<ConversationView> conversationViewRes=conversationViewRepository.findByProductIdAndReceiverIdOrSenderId(product,user,user);
        model.addAttribute("conversationView",conversationViewRes);
        return "conversation";
    }
}
