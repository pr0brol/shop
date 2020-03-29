package springmarket.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmarket.shop.entities.User;
import springmarket.shop.repositories.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService {
    private OrderItemRepository orderItemRepository;

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

    public List<Long> findByProductIdAndUser (Long productId, User user) {
        return orderItemRepository.findId(productId, user);
    }
}
