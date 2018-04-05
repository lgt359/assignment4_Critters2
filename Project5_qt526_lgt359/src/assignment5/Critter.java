package assignment5;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}

	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background
	 *
	 * critters must override at least one of the following three methods, it is not
	 * proper for critters to remain invisible in the view
	 *
	 * If a critter only overrides the outline color, then it will look like a non-filled
	 * shape, at least, that's the intent. You can edit these default methods however you
	 * need to, but please preserve that intent as you implement them.
	 */
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.WHITE;
	}

	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }

	public abstract CritterShape viewShape();

	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	protected final String look(int direction, boolean steps) {
		int newX = this.x_coord;
		int newY = this.y_coord;
		int number_steps;

		// false = 1 step; true = 2 steps
		if(steps){
			number_steps = 2;
		}
		else {
			number_steps = 1;
		}

		switch (direction) {
			case 0: {
				newX += number_steps;
				break;
			}

			case 1: {
				newX += number_steps;
				newY -= number_steps;
				break;
			}

			case 2: {
				newY -= number_steps;
				break;
			}

			case 3: {
				newX -= number_steps;
				newY -= number_steps;
				break;
			}

			case 4: {
				newX -= number_steps;
				break;
			}

			case 5: {
				newX -= number_steps;
				newY += number_steps;
				break;
			}

			case 6: {
				newY += number_steps;
				break;
			}

			case 7: {
				newX += number_steps;
				newY += number_steps;
				break;
			}
		}

		// fixes wrap around coordinates
		if (newX < 0) {
			newX += Params.world_width;
		}

		if (newX >= Params.world_width) {
			newX = Params.world_width % newX;
		}

		if (newY < 0) {
			newY += Params.world_height;
		}

		if (newY >= Params.world_height) {
			newY = Params.world_height % newY;
		}

		// if critter in spot, return critter.toString
		for (Critter c : population) {
			if ((newX == c.x_coord) && (newY == c.y_coord)) {
				return c.toString();
			}
		}

		// No critter in spot, return null
		return null;
	}

	/* rest is unchanged from Project 4 */

	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}

	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

	private int energy = 0;
	protected int getEnergy() { return energy; }

	// Data
	private int x_coord;
	private int y_coord;
	private boolean hasMoved;
	private boolean tryingToRun;

	/** Moves critter one spot in given direction, 0-7.
	 *  Deducts energy cost from critter's current energy state.
	 * @param direction
	 */
	protected final void walk(int direction) {
		if (!this.hasMoved) {
			move(direction, 1);
			this.hasMoved = true;		// raises the flag
		}

		this.energy -= Params.walk_energy_cost;     // deduct energy cost
	}

	/** Moves critter two spots in given direction, 0-7.
	 * Deducts energy cost fromt critter's current energy state.
	 * @param direction
	 */
	protected final void run(int direction) {
		if (!this.hasMoved) {
			if(tryingToRun){
				runAway();
			}
			else {
				move(direction, 2);
				this.hasMoved = true;		// raises the flag
			}
		}

		this.energy -= Params.run_energy_cost;     // deduct energy cost
	}

	/** Checks if critter's run away position is occupied by another.
	 *  Critter always runs away towards the right (direction 0)
	 *  If occupied, critter can not run away.
	 *  If not occupied, updates critter's position.
	 */
	private final void runAway() {
		boolean isOccupied = false;

		if (!this.hasMoved) {

			int x_new_coord = this.x_coord + 2;

			if (x_new_coord >= Params.world_width){
				x_new_coord = Params.world_width % x_coord;
			}

			for (Critter c : population) {
				if ((x_new_coord == c.x_coord) && (this.y_coord == c.y_coord)) {
					isOccupied = true;
					break;
				}
			}

			if (!isOccupied) {
				move(0, 2);
			}
			this.hasMoved = true;		// raises the flag
		}
	}

	/** Moves critter in given direction and steps
	 * @param direction 0-7, representing where to move critter
	 * @param steps int tha represents how many steps critter takes
	 */
	private void move( int direction, int steps) {

		switch (direction) {
			case 0: {            // move east (step) units
				x_coord += steps;
				break;
			}

			case 1: {            // move northeast (step) units
				x_coord += steps;
				y_coord -= steps;
				break;
			}

			case 2: {            // move north (step) units
				y_coord -= steps;
				break;
			}

			case 3: {            // move northwest (step) units
				x_coord -= steps;
				y_coord -= steps;
				break;
			}

			case 4: {            // move west (step) units
				x_coord -= steps;
				break;
			}

			case 5: {            // move southwest (step) units
				x_coord -= steps;
				y_coord += steps;
				break;
			}

			case 6: {            // move south (step) units
				y_coord += steps;
				break;
			}

			case 7: {            // southeast (step) units
				x_coord += steps;
				y_coord += steps;
				break;
			}
		}

		// Fixes wrap around coordinates
		if (x_coord < 0) {
			x_coord += Params.world_width;
		}

		if (x_coord >= Params.world_width) {
			x_coord = Params.world_width % x_coord;
		}

		if (y_coord < 0) {
			y_coord += Params.world_height;
		}

		if (y_coord >= Params.world_height) {
			y_coord = Params.world_height % y_coord;
		}
	}

	/** Adds new offspring critter to world
	 * @param offspring New Critter that we are adding
	 * @param direction the direction where we place new critter in reference to parent's position
	 */
	protected final void reproduce(Critter offspring, int direction) {

		// check if critter has enough energy to reproduce
		if(this.energy < Params.min_reproduce_energy){
			return;
		}

		// initialize energy levels
		offspring.energy = this.energy / 2;					// half of parent's energy
		this.energy = (int) Math.ceil(this.energy / 2);

		switch (direction) {
			case 0: {
				offspring.x_coord = this.x_coord + 1;
				offspring.y_coord = this.y_coord;
				break;
			}

			case 1: {
				offspring.x_coord = this.x_coord + 1;
				offspring.y_coord = this.y_coord - 1;
				break;
			}

			case 2: {
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.y_coord - 1;
				break;
			}

			case 3: {
				offspring.x_coord = this.x_coord - 1;
				offspring.y_coord = this.y_coord - 1;
				break;
			}

			case 4: {
				offspring.x_coord = this.x_coord - 1;
				offspring.y_coord = this.y_coord;
				break;
			}

			case 5: {
				offspring.x_coord = this.x_coord - 1;
				offspring.y_coord = this.y_coord + 1;
				break;
			}

			case 6: {
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.y_coord + 1;
				;
				break;
			}

			case 7: {
				offspring.x_coord = this.x_coord + 1;
				offspring.y_coord = this.y_coord + 1;
				break;
			}
		}

		// Fixes wrap around coordinate
		if (offspring.x_coord < 0) {
			offspring.x_coord += Params.world_width;
		}

		if (offspring.x_coord >= Params.world_width) {
			offspring.x_coord = Params.world_width % offspring.x_coord;
		}

		if (offspring.y_coord < 0) {
			offspring.y_coord += Params.world_height;
		}

		if (offspring.y_coord >= Params.world_height) {
			offspring.y_coord = Params.world_height % offspring.y_coord;
		}

		// add to list of babies
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);

	public static void worldTimeStep() {}

	/** Alternate displayWorld, where you use Main.<pane> to reach into your display component.
	 * public static void displayWorld() {}
	 */
	public static void displayWorld(GridPane pane) {
	}

	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {

		try {
			// create new critter
			Class<?> c = Class.forName(myPackage + "." + critter_class_name);
			Critter newCritter = (Critter) c.newInstance();
			population.add(newCritter);

			// initialize critter  values
			newCritter.x_coord = getRandomInt(Params.world_width);
			newCritter.y_coord = getRandomInt(Params.world_height);
			newCritter.energy = Params.start_energy;

		}
		catch(InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}

	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new ArrayList<Critter>();

		// Initialize and get critter class
		Class<?> critter = null;
		try {
			// get class object corresponding to critter class name
			critter = Class.forName(myPackage + "." + critter_class_name);
		}
		catch (ClassNotFoundException cnfe) {
			throw new InvalidCritterException(critter_class_name);
		}

		// add critters to result
		for (Critter c : population) {
			if (critter.isInstance(c)) {
				result.add(c);
			}
		}

		return result;
	}

	public static String runStats(List<Critter> critters) {
		return null;
	}

	/* the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here.
	 *
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}


		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}

}
