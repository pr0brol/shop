package springmarket.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springmarket.shop.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o.id from Order o where o.id = ?1")
    Order findId(Long id);

    @Query("update Order set status = ?2 where id = ?1")
    void update(Long id, String status);
}
