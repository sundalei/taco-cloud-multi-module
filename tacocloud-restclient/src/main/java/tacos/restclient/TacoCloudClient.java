package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.Ingredient;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TacoCloudClient {

    private final RestTemplate rest;
    private final Traverson traverson;

    @Autowired
    public TacoCloudClient(RestTemplate rest, Traverson traverson) {
        this.rest = rest;
        this.traverson = traverson;
    }

    public Ingredient getIngredientById(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/ingredients/{id}")
                .build(urlVariables);

        ResponseEntity<Ingredient> responseEntity = rest.getForEntity(url, Ingredient.class);

        log.info(responseEntity.getHeaders().toString());

        return responseEntity.getBody();
    }
}
