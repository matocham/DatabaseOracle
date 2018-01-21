package pl.edu.pb.wi.project.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Conversation;
import pl.edu.pb.wi.project.database.models.Offer;
import pl.edu.pb.wi.project.database.models.Product;

import java.util.List;


public interface ConversationRepository extends CrudRepository<Conversation,Long> {
    Conversation findById(Long Id);
    List<Conversation> findByProductId(Product product);

}
