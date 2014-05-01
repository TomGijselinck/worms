package worms.gui.game.sprites;

import worms.gui.game.ImageSprite;
import worms.gui.game.PlayGameScreen;
import worms.model.Food;

public class FoodSprite extends ImageSprite<Food> {

	private static final double MAX_SCALE = 100;
	private static final double MIN_SCALE = 0.05;

	private final Food food;

	public FoodSprite(PlayGameScreen screen, Food food) {
		super(screen, "images/burger.png");
		this.food = food;
	}

	/**
	 * @param radius
	 *            (in worm-meter)
	 */
	public void setRadius(double radius) {
		/*
		 * Height of the image (when drawn at native size) in worm-meters, given
		 * the scale at which the world is drawn to screen
		 */
		double imageHeightInMeters = getScreen().screenToWorldDistance(
				getImageHeight());

		/*
		 * scale factor to nicely fit the image in a circle with diameter equal
		 * to the image height (value determined experimentally)
		 */
		double fitFactor = 1.2;

		double scaleFactor = fitFactor * 2 * radius / imageHeightInMeters;

		scaleFactor = Math.max(MIN_SCALE, Math.min(scaleFactor, MAX_SCALE));

		setScale(scaleFactor);
	}

	@Override
	public Food getObject() {
		return getFood();
	}

	public Food getFood() {
		return food;
	}
	
	@Override
	public boolean isObjectAlive() {
		return getFacade().isActive(food);
	}

}
