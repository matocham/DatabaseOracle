package pl.edu.pb.wi.project.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findById(long primaryKey);
    List<Product> findByOwnerId(long foreginKey);
}
