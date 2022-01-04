package tacos.mongo;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mongodb.dsl.MongoDb;

@Configuration
public class TacoOrderMongoIntegrationConfig {

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(), "tacos");
    }

    @Bean
    public IntegrationFlow tacoOrderMongoFlow(
            MongoRecordToOrderTransformer mongoRecordToOrderTransformer) {
        return IntegrationFlows.from(
                        MongoDb.inboundChannelAdapter(mongoDatabaseFactory(), "{'type' : 'order'}"),
                        sourcePollingChannelAdapterSpec ->
                                sourcePollingChannelAdapterSpec.poller(Pollers.fixedDelay(30000)))
                .transform(mongoRecordToOrderTransformer)
                .get();
    }
}
