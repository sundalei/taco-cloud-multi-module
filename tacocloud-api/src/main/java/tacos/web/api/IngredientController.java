package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.Optional;

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
    public Iterable<Ingredient> allIngredients() {
        return ingredientRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") String id) {
        Optional<Ingredient> optional = ingredientRepo.findById(id);
        return optional.map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public void updateIngredient(@PathVariable("id") String id, @RequestBody Ingredient ingredient) {
        if (!ingredient.getId().equals(id)) {
            throw new IllegalStateException("Given ingredient's ID doesn't match the ID in the path.");
        }
        ingredientRepo.save(ingredient);
    }
}
