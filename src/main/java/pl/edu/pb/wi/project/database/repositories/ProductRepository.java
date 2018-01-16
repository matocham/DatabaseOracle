package pl.edu.pb.wi.project.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByCategoryId(Long catId);

    @Query(value = "SELECT * FROM PRODUCT WHERE category_id in (select id from category START WITH id = ?1 CONNECT BY PRIOR id = parentCategory)", nativeQuery = true)
    List<Product> findByCategoryTree(Long startingId);
}
