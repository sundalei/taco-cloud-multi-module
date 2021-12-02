package tacos.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;
import tacos.Order;

import javax.jms.Destination;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

    private JmsTemplate jms;

    private Destination orderQueue;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
        this.jms = jms;
        this.orderQueue = orderQueue;
    }

    @Override
    public void sendOrder(Order order) {
        //jms.send(orderQueue, session -> session.createObjectMessage(order));
        jms.convertAndSend(orderQueue, order, (MessagePostProcessor) message -> {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }
}
