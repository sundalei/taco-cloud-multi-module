package tacos.messaging;

import tacos.Order;

/**
 * Order messaging service.
 * @author dalei
 */
public interface OrderMessagingService {

    void sendOrder(Order order);
}
