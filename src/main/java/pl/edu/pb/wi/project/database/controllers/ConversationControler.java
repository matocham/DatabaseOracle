package pl.edu.pb.wi.project.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pb.wi.project.database.models.*;
import pl.edu.pb.wi.project.database.repositories.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    MessageRepository messageRepository;
    List<ConversationView> conversationViewRes;
    Product product;
    @RequestMapping("/conversation")
    String getConversation(@RequestParam(name = "id") Long productId, Model model, HttpSession session){
        Long receiverId=Long.parseLong(session.getAttribute(USER_ID_SESSION).toString());
        product= productRepository.findById(productId);
        Users user= usersRepository.findById(receiverId);
        List<Conversation> conversation= conversationRepository.findByProductId(product);
        Conversation gcon=null;
        for (Conversation c:conversation
             ) {
            if(c.getInitSender().getId()==receiverId||c.getInitReciver().getId()==receiverId){
                gcon=c;
                break;
            }
        }
        conversationViewRes=conversationViewRepository.findConversationViewByParameters(product,user);
        List<Message> messages=messageRepository.findByConversation(gcon);
        model.addAttribute("conversationView",conversationViewRes);
        model.addAttribute("messages",messages);
        return "conversation";
    }
    @RequestMapping("/sendMessage")
    String sendMessage(@RequestParam() String description,Model model,HttpSession session){
        ConversationView conversationView=new ConversationView();
        Long sender=Long.parseLong(session.getAttribute(USER_ID_SESSION).toString());
        Users user1= usersRepository.findById(sender);

        conversationView.setSenderId(user1);
        conversationView.setMsgBody(description);
        conversationView.setDisplayed(false);
        conversationView.setSendDate(new Date());
        conversationView.setReceiverId(product.getOwner());
       if(conversationViewRes!=null && conversationViewRes.size()>0) {
           conversationView.setId(conversationViewRes.get(0).getId());
       }
       else{

       }
        conversationView.setProductId(product);
        conversationViewRepository.save(conversationView);
        return "redirect:/conversation?id="+product.getId();
    }
}
