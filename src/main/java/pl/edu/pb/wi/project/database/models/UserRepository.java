package pl.edu.pb.wi.project.database.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface UserRepository  extends CrudRepository<User, Long> {
    @Procedure(name = "finalize_exchange")
    void finalizeExchange(@Param("offer_id") Long id);

    List<User> findByEmail(String email);


    // custom query example and return a stream
    @Query("select c from User c where c.email = :email")
    Stream<User> findByEmailReturnStream(@Param("email") String email);
}
