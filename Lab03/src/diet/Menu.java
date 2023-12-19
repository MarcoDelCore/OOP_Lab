package diet;


/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	String name;
	Food food;
	double totProteins, totCarbs, totCalories, totFat;
	boolean per100g;
	double actualQuantity;
	
	
	public Menu(String name, Food food) {
		this.food = food;
		this.name = name;
		totProteins = 0;
		totCarbs = 0;
		totCalories = 0;
		totFat = 0;
		actualQuantity = 0;
	}
	
	
	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
    public Menu addRecipe(String recipe, double quantity) {
    	actualQuantity = quantity;
    	totCarbs += food.getRecipe(recipe).getCarbs() * (quantity/100);
    	totCalories += food.getRecipe(recipe).getCalories() * (quantity/100);
    	totFat += food.getRecipe(recipe).getFat() * (quantity/100);
    	totProteins += food.getRecipe(recipe).getProteins() * (quantity/100);
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
    public Menu addProduct(String product) {
    	totCarbs += food.getProduct(product).getCarbs();
    	totCalories += food.getProduct(product).getCalories();
    	totFat += food.getProduct(product).getFat();
    	totProteins += food.getProduct(product).getProteins();
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return totCalories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return totProteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return totCarbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return totFat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return false;
	}
}