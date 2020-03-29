package springmarket.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springmarket.shop.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
