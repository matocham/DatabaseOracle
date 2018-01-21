package pl.edu.pb.wi.project.database.repositories;

import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query(value = "SELECT * FROM PRODUCT WHERE exchanged = 0 and category_id in (select id from category START WITH id = ?1 CONNECT BY PRIOR id = parentCategory)", nativeQuery = true)
    List<Product> findByCategoryTree(Long startingId);

    @Query(value = "SELECT * FROM PRODUCT WHERE exchanged = 0 and category_id in (select id from category START WITH id = ?1 CONNECT BY PRIOR id = parentCategory) and owner_id = ?2", nativeQuery = true)
    List<Product> findByCategoryTreeAndOwner(Long startingId, Long ownerId);

    List<Product> findByOwnerId(Long id);

    List<Product> findByOwnerIdAndExchanged(Long id, Boolean exchanged);

    List<Product> findByExchangedFalseOrderByAddDateDesc();

    List<Product> findProductByOwnerId(Long ownerId);
}
