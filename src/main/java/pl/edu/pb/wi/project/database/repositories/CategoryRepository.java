package pl.edu.pb.wi.project.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.project.database.models.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findByParentCategoryId(Long parent);
    List<Category> findAll();
}
