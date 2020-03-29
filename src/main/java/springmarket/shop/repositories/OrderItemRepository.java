package springmarket.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springmarket.shop.entities.OrderItem;
import springmarket.shop.entities.User;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select o.id from OrderItem o where o.product.id = ?1 and o.order.user = ?2")
    List<Long> findId(Long prodId, User user);
}
