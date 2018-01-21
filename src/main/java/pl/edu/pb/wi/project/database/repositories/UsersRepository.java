package pl.edu.pb.wi.project.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pb.wi.project.database.models.Users;

import java.util.List;
import java.util.stream.Stream;

public interface UsersRepository extends CrudRepository<Users, Long> {

    List<Users> findByEmail(String email);

    Users findById(long primaryKey);

    // custom query example and return a stream
    @Query("select c from Users c where c.email = :email")
    Stream<Users> findByEmailReturnStream(@Param("email") String email);

    // Find by login
    @Query("select u from Users u where u.login = ?1")
    List<Users> findByLogin(String login);

    @Query(value = "select * from Users where login =  ?1 and user_password = utilities.get_hash_val( ?2 )", nativeQuery = true)
    List<Users> findUsersByLoginAndPassword(String login, String password);
}
