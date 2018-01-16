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

    //@Function(name = "get_hash_val")
    //void getHashPassword(@Param("p_in") String password);
    // Set user password by login
    //@Modifying
    //@Query("update Users u set u.user_password = ?1 where u.login = ?2")
    //int setPasswordByLogin(String password, String login);

    // Set user password by id
    //@Modifying
    //@Query("update Users u set u.user_password = ?1 where u.id = ?2")
    //int setPasswordById(String password, Long id);

    //@Modifying
    //@Query("insert into Users as u (u.id, u.name, u.last_name, u.login, u.user_password, u.email, u.city) values (USERS_SEQ.NEXTVAL, :name, :lastName, :login, :password, :email, :city)")
    //void insertUser(@Param("name") String name, @Param("lastName") String lastName, @Param("login") String login, @Param("password") String password, @Param("email") String email, @Param("city") String city);
}
