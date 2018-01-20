package pl.edu.pb.wi.project.database.repositories.view;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.view.OfferedMe;

import java.util.List;

public interface OfferedMeRepository extends CrudRepository<OfferedMe, Long> {
    List<OfferedMe> findByOwnerLogin(String login);
}
