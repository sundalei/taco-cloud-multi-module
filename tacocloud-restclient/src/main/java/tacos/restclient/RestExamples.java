package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;

import java.net.URI;
import java.util.List;

@SpringBootConfiguration
@ComponentScan
@Slf4j
public class RestExamples {

    public static void main(String[] args) {
        SpringApplication.run(RestExamples.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Traverson traverson() {
        Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
        return traverson;
    }

    /*
    @Bean
    public CommandLineRunner fetchIngredients(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- GET -------------------------");
            log.info("GETTING INGREDIENT BY IDE");
            log.info("Ingredient:  " + tacoCloudClient.getIngredientById("CHED"));
            log.info("GETTING ALL INGREDIENTS");
            List<Ingredient> ingredients = tacoCloudClient.getAllIngredients();
            for (Ingredient ingredient : ingredients) {
                log.info("   -  " + ingredient);
            }
        };
    }
    */


    @Bean
    public CommandLineRunner putAnIngredient(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- PUT -------------------------");
            Ingredient before = tacoCloudClient.getIngredientById("LETC");
            log.info("BEFORE: " + before);
            tacoCloudClient.updateIngredient(
                    new Ingredient("LETC", "Shredded Lettuce", Ingredient.Type.VEGGIES));
            Ingredient after = tacoCloudClient.getIngredientById("LETC");
            log.info("AFTER: " + after);
        };
    }
}
