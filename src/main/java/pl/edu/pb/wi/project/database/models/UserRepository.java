package pl.edu.pb.wi.project.database.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface UserRepository  extends CrudRepository<Users, Long> {
    @Procedure(name = "finalize_exchange")
    void finalizeExchange(@Param("offer_id") Long id);

    List<Users> findByEmail(String email);


    // custom query example and return a stream
    @Query("select c from Users c where c.email = :email")
    Stream<Users> findByEmailReturnStream(@Param("email") String email);
}
