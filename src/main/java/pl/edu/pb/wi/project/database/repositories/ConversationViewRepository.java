package pl.edu.pb.wi.project.database.repositories;


import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Category;
import pl.edu.pb.wi.project.database.models.ConversationView;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.models.Users;

import java.util.List;

public interface ConversationViewRepository extends CrudRepository<ConversationView, Long> {
    public List<ConversationView> findByProductIdAndReceiverIdOrSenderId(Product productId, Users receiverId,Users senderId);
}
