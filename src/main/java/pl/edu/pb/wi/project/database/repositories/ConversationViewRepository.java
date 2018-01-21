package pl.edu.pb.wi.project.database.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Category;
import pl.edu.pb.wi.project.database.models.ConversationView;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.models.Users;

import java.util.List;

public interface ConversationViewRepository extends CrudRepository<ConversationView, Long> {
    //List<ConversationView> findByProductIdAndReceiverIdOrSenderId(Product productId, Users receiverId,Users senderId);
    @Query(value = "select * from conversation_heading where product_id =  ?1 and (receiver= ?2 or (sender = ?2)) ", nativeQuery = true)
    List<ConversationView> findConversationViewByParameters(Product productId, Users receiverId);


}
