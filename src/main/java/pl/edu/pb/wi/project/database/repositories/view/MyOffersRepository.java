package pl.edu.pb.wi.project.database.repositories.view;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.view.MyOffers;

import java.util.List;

public interface MyOffersRepository extends CrudRepository<MyOffers, Long> {
    List<MyOffers> findByOwnerLogin(String login);
}
