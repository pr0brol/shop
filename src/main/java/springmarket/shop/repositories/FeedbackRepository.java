package springmarket.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springmarket.shop.entities.Feedback;
import springmarket.shop.entities.User;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback> {
    @Query("select f.prod_id from Feedback f where f.prod_id = ?1")
    Long findId(Long prodId);

}
