package tacos.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<Order> {

    private RestTemplate restTemplate;
    private ApiProperties apiProperties;

    @Autowired
    public OrderSubmitMessageHandler(ApiProperties apiProperties, RestTemplate restTemplate) {
        this.apiProperties = apiProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public Object handle(Order payload, MessageHeaders headers) {
        restTemplate.postForObject(apiProperties.getUrl(), payload, String.class);
        return null;
    }
}
