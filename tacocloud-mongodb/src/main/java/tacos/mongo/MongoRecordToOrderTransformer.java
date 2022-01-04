package tacos.mongo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MongoRecordToOrderTransformer implements Transformer {
    @Override
    public Message<?> transform(Message<?> message) {
        log.info(message.getPayload().toString());
        return null;
    }
}
