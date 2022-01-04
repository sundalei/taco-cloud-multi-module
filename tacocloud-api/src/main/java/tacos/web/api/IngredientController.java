package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.net.URI;

@RestController
@RequestMapping(path = "/ingredients", produces = "application/json")
@CrossOrigin("*")
public class IngredientController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping
    public Flux<Ingredient> allIngredients() {
        return ingredientRepo.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Ingredient> getIngredientById(@PathVariable("id") String id) {
        return ingredientRepo.findById(id);
    }

    @PutMapping("/{id}")
    public void updateIngredient(@PathVariable("id") String id, @RequestBody Ingredient ingredient) {
        if (!ingredient.getId().equals(id)) {
            throw new IllegalStateException("Given ingredient's ID doesn't match the ID in the path.");
        }
        ingredientRepo.save(ingredient);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable String id) {
        ingredientRepo.deleteById(id);
    }

    @PostMapping
    public Mono<ResponseEntity<Ingredient>> newIngredient(@RequestBody Mono<Ingredient> newIngredient) {
        return newIngredient
                .flatMap(ingredientRepo::save)
                .map(ingredient -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(URI.create("http://localhost:8080/ingredients/" + ingredient.getId()));
                    return new ResponseEntity<>(ingredient, headers, HttpStatus.CREATED);
                });
    }
}
