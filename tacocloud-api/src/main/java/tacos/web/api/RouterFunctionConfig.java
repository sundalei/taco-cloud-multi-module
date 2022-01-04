package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import tacos.Taco;
import tacos.data.TacoRepository;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    private final TacoRepository tacoRepo;

    @Autowired
    public RouterFunctionConfig(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/design/taco"), this::recents);
    }

    public Mono<ServerResponse> recents(ServerRequest request) {
        return ServerResponse.ok()
                .body(tacoRepo.findAll().take(12), Taco.class);
    }
}
