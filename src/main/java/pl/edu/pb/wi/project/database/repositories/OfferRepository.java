package pl.edu.pb.wi.project.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pb.wi.project.database.models.Offer;
import pl.edu.pb.wi.project.database.models.Product;
import pl.edu.pb.wi.project.database.models.Users;

import java.util.List;

public interface OfferRepository extends CrudRepository<Offer,Long> {
    @Procedure(name = "finalize_exchange")
    void finalizeExchange(@Param("offer_id") Long id);

}
