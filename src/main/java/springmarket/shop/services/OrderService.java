package springmarket.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmarket.shop.entities.Order;
import springmarket.shop.repositories.OrderRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order save(Order order){
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findId(id);
    }

    public void updateOrder(Order order) {
        orderRepository.update(order.getId(), order.getStatus());
    }

}
