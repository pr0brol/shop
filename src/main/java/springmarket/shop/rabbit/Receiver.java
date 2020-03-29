package springmarket.shop.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springmarket.shop.controllers.OrderController;

@Component
public class Receiver {
    private RabbitTemplate rabbitTemplate;
    private OrderController orderController;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public void receiveMessageFromServer(byte[] message) {
        String msg = new String(message);
        orderController.setStatus(msg);
    }

}
