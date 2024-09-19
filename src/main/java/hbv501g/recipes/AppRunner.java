/**
 * run aðferðin keyrir / prentar í terminal í hvert skipti 
 * sem eitthvað er gert með localhost dæmið
 */
package hbv501g.recipes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import hbv501g.recipes.model.Ingredient;
import hbv501g.recipes.service.IngredientService;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    IngredientService ingredientService;

    // Þetta keyrist alltaf þegar localhost dæmið refreshast.
    @Override
    public void run(String... args) throws Exception {

        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        ingredients.forEach((i) -> System.out.println(i.getName() + " " + i.getPrice()));

    }
}
