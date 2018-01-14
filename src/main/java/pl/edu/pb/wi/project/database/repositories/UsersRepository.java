package pl.edu.pb.wi.project.database.repositories;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pb.wi.project.database.models.Users;

import java.util.List;
import java.util.stream.Stream;

public interface UsersRepository extends CrudRepository<Users, Long> {
    @Procedure(name = "finalize_exchange")
    void finalizeExchange(@Param("offer_id") Long id);

    List<Users> findByEmail(String email);

    Users findById(long primaryKey);

    // custom query example and return a stream
    @Query("select c from Users c where c.email = :email")
    Stream<Users> findByEmailReturnStream(@Param("email") String email);
}
