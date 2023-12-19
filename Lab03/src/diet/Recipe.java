package diet;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement{
	String name;
	private Food food;
	List<Ingredient> ingredients;
	double totProteins, totCarbs, totFat, totCalories;
	double actualQuantity;
	
	public Recipe(String name, Food food) {
		this.name = name;
		this.food = food;
		this.ingredients = new ArrayList<>();
		this.actualQuantity = 0;
		this.totCalories = 0;
		this.totCarbs = 0;
		this.totFat = 0;
		this.totProteins = 0;
	}
	
	
	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		Ingredient tmp = new Ingredient(material, quantity);
		ingredients.add(tmp);
		totProteins += food.getRawMaterial(material).getProteins() * (quantity/100);
		totCarbs += food.getRawMaterial(material).getCarbs() * (quantity/100);
		totFat += food.getRawMaterial(material).getFat() * (quantity/100);
		totCalories += food.getRawMaterial(material).getCalories() * (quantity/100);
		actualQuantity += quantity;
		return this;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}


	@Override
	public double getCalories() {
		// TODO Auto-generated method stub
		return totCalories * (100/actualQuantity);
	}


	@Override
	public double getProteins() {
		// TODO Auto-generated method stub
		return totProteins * (100/actualQuantity);
	}


	@Override
	public double getCarbs() {
		// TODO Auto-generated method stub
		return totCarbs * (100/actualQuantity);
	}


	@Override
	public double getFat() {
		// TODO Auto-generated method stub
		return totFat * (100/actualQuantity);
	}


	@Override
	public boolean per100g() {
		// TODO Auto-generated method stub
		return true;
	}

	
	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Ingredient i : ingredients) {
			s.append(i.name+"\n");
		}
		return s.toString().trim();
	}
}
