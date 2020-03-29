package springmarket.shop.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public String sendMessage(String string) {
        System.out.println("Sending message..." + string);
        rabbitTemplate.convertAndSend(RabbitmqApplication.EXCHANGE_NAME_SHOP,null, string);
        return "OK";
    }
}
