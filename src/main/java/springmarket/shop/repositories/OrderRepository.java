package springmarket.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springmarket.shop.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.id = ?1")
    Order findOrderById(Long id);

    @Transactional
    @Modifying
    @Query("update Order o set o.status = ?2 where o.id = ?1")
    void update(Long id, String status);
}
