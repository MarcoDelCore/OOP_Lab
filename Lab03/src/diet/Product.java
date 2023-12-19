package diet;

public class Product implements NutritionalElement {
	private String name;
	private double calories, proteins, carbs, fat;
	private boolean per100g;
	
	public Product (String n, double c, double p ,double ca, double f, boolean t) {
		name = n;
		calories = c;
		proteins = p;
		carbs = ca;
		fat = f;
		per100g = t;
	}
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public double getCalories() {
		// TODO Auto-generated method stub
		return calories;
	}

	@Override
	public double getProteins() {
		// TODO Auto-generated method stub
		return proteins;
	}

	@Override
	public double getCarbs() {
		// TODO Auto-generated method stub
		return carbs;
	}

	@Override
	public double getFat() {
		// TODO Auto-generated method stub
		return fat;
	}

	@Override
	public boolean per100g() {
		// TODO Auto-generated method stub
		return per100g;
	}


}
