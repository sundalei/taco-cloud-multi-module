package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tacos.data.IngredientRepository;

@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Ingredient flourTortilla = saveAnIngredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
                Ingredient cornTortilla = saveAnIngredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP);
                Ingredient groundBeef = saveAnIngredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
                Ingredient carnitas = saveAnIngredient("CARN", "Carnitas", Ingredient.Type.PROTEIN);
                Ingredient tomatoes = saveAnIngredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES);
                Ingredient lettuce = saveAnIngredient("LETC", "Lettuce", Ingredient.Type.VEGGIES);
                Ingredient cheddar = saveAnIngredient("CHED", "Cheddar", Ingredient.Type.CHEESE);
                Ingredient jack = saveAnIngredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE);
                Ingredient salsa = saveAnIngredient("SLSA", "Salsa", Ingredient.Type.SAUCE);
                Ingredient sourCream = saveAnIngredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE);

            }

            private Ingredient saveAnIngredient(String id, String name, Ingredient.Type type) {
                Ingredient ingredient = new Ingredient(id, name, type);
                repo.save(ingredient).subscribe();
                return ingredient;
            }
        };
    }
}
