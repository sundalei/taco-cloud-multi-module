package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.Ingredient;
import tacos.Taco;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

    public List<Ingredient> getAllIngredients() {
        return rest.exchange("http://localhost:8080/ingredients",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {})
                .getBody();
    }

    public void updateIngredient(Ingredient ingredient) {
        rest.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
    }

    public void deleteAnIngredient(String id) {
        rest.delete("http://localhost:8080/ingredients/{id}", id);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
                rest.postForEntity("http://localhost:8080/ingredients", ingredient, Ingredient.class);

        log.info(responseEntity.getHeaders().toString());
        return responseEntity.getBody();
    }



    /*
    public URI createIngredient(Ingredient ingredient) {
        return rest.postForLocation("http://localhost:8080/ingredients", ingredient);
    }

     */

    public void listIngredients() {

        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
                new ParameterizedTypeReference<>() {};

        CollectionModel<Ingredient> ingredientRes =
                traverson
                .follow("ingredients")
                .toObject(ingredientType);

        Collection<Ingredient> ingredients = ingredientRes.getContent();
        log.info("ingredients");
        ingredients.forEach(ingredient -> log.info(ingredient.toString()));
    }

    public void recentTacos() {

        ParameterizedTypeReference<CollectionModel<Taco>> tacoType =
                new ParameterizedTypeReference<>() {};

        CollectionModel<Taco> tacoRes =
                traverson
                        .follow("tacos")
                        .follow("recents")
                        .toObject(tacoType);
        Collection<Taco> tacos = tacoRes.getContent();
        tacos.forEach(taco -> log.info(taco.toString()));
    }
}
