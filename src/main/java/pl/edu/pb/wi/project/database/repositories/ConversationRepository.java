package pl.edu.pb.wi.project.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Conversation;
import pl.edu.pb.wi.project.database.models.Offer;
import pl.edu.pb.wi.project.database.models.Product;

import java.util.List;


public interface ConversationRepository extends CrudRepository<Conversation,Long> {
    Conversation findById(Long Id);
    List<Conversation> findByProductId(Product product);

    @Query(value = "select * from conversation where (init_receiver = ?1 or init_sender = ?1) and product_id= ?2", nativeQuery = true)
    List<Conversation> findConversations(Long userID, Long productId);
}
