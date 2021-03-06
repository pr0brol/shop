package springmarket.shop.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqApplication {
    private static final String EXCHANGE_NAME_SERVER = "exchangeServer";
    static final String EXCHANGE_NAME_SHOP = "exchangeShop";
    private static final String QUEUE_NAME = "queueFromServer";

    @Bean
    Queue queueTopic() {
        return new Queue(QUEUE_NAME, true, false, true);
    }

    @Bean
    FanoutExchange fanoutExchangeServer() {
        return new FanoutExchange(EXCHANGE_NAME_SERVER);
    }

    @Bean
    Binding bindingFanout(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    SimpleMessageListenerContainer containerForTopic(ConnectionFactory connectionFactory, @Qualifier("listenerAdapterForTopic") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        bindingFanout(queueTopic(), fanoutExchangeServer());
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterForTopic(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageFromServer");
    }

}
