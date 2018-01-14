package pl.edu.pb.wi.project.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Offer;
import pl.edu.pb.wi.project.database.models.Users;

public interface OfferRepository  extends CrudRepository<Offer, Long> {
    Offer findById(long primaryKey);
}

