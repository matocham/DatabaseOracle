package pl.edu.pb.wi.project.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Conversation;
import pl.edu.pb.wi.project.database.models.Message;

import java.util.List;


public interface MessageRepository extends CrudRepository<Message,Long> {
    List<Message> findByConversation(Conversation conversation);
}
