/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Lorrie Tria
 * LGT359
 * Quoc Truong
 * QT526
 *
 * Spring 2018
 */
package assignment5;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

	private static String myPackage;

	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	// Stages and Main Panes
	private static Stage controlStage = new Stage();
	private static GridPane paneController = new GridPane();
	private static GridPane world = new GridPane();
	static GridPane scene = new GridPane();

	// Flags
	private static boolean isAnimating = false;
	private static boolean isStatsRunning = false;

	// Error and Alerts
	private static Stage alert = new Stage();
	static GridPane errorPane = new GridPane();
	static TextArea errorText = new TextArea();
	private static Button errorButton = new Button("Close Error");

	// Make Critters Section
	private static ComboBox<String> listOfCritters = new ComboBox<>();
	private static Button makeButton = new Button("Make Critters");
	private static Label critLabel = new Label("Number of Critters");
	private static Slider critSlider = new Slider(1, 1000, 1);

	// Quit
	private static Button quitButton = new Button("Quit");

	// Run Stats Section
	private static TextArea statsWindow = new TextArea();
	private static Button runStatsButton = new Button("Run Stats");
	private static Button stopStatsButton = new Button("Stop Stats");
	private static ComboBox<String> runStatsComboBox = new ComboBox<>();

	// Step Section
	private static Label stepLabel = new Label("Number of Steps");
	private static Button stepButton = new Button("Step");
	private static Slider stepSlider = new Slider(1, 100, 1);

	// Seed
	private static TextField seedText = new TextField();
	private static Button seedButton = new Button("Set Seed");

	// Animation
	private static Button animateButton = new Button("Run");
	private static Button pauseButton = new Button("Stop");
	private static Label animateLabel = new Label("Speed of Animation");
	private static Slider animationSpeed = new Slider(1, 200, 1);

	/** Runs and launches main
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/** Overrides start and implements GUI
	 * @param primaryStage primary Stage of GUI
	 * @throws ClassNotFoundException
	 */
	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException{
		primaryStage.setTitle("Critters GUI");
		primaryStage.setTitle("Control Panel");

		// Initialization
		worldInit();
		makeInit();
		exitInit();
		seedInit();
		stepInit();
		aniInit();
		statsInit();

		primaryStage.setScene(new Scene(world, 1000, 1000));
		primaryStage.show();

		controlStage.setScene(new Scene(paneController, 500, 500));
		controlStage.show();
	}


	/** Initializes world pane, which is where animation takes place.
	 *  Shows grid lines corresponding to coordinate axis of critters.
	 */
	private static void worldInit() {
		world.setGridLinesVisible(true);

		// Sets columns
		for(int i = 0; i < Params.world_width; i++) {
			ColumnConstraints colC = new ColumnConstraints();
			colC.setPercentWidth(100.0 / Params.world_width);
			colC.setHalignment(HPos.CENTER);
			world.getColumnConstraints().add(colC);
		}

		// Sets rows
		for(int j = 0; j < Params.world_height; j++) {
			RowConstraints rowC = new RowConstraints();
			rowC.setPercentHeight(100.0 / Params.world_height);
			rowC.setValignment(VPos.CENTER);
			world.getRowConstraints().add(rowC);
		}

	}

	/** If button is pressed, program terminates and closes
	 */
	private static void exitInit() {
		quitButton.setOnAction(e -> {
			System.exit(0);
		});

		// Setting place and margins of quit button
		GridPane.setConstraints(quitButton, 0, 25, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setMargin(quitButton, new Insets(10, 5, 10, 5));
		paneController.add(quitButton, 0, 25);
	}

	/** Initializes "make critters" portion of control panel
	 *  4 Elements: Combo box, Label, Slider, Button
	 */
	private void makeInit() {

		// Configures ListOfCritters Combo Box drop down
		try {
			listOfCritters = comboConfig(listOfCritters);
		} catch (ClassNotFoundException cnfe) {
			errorInit("ERROR: Class not found");
		} catch (URISyntaxException use) {
			errorInit("ERROR: Syntax Exception");
		}

		// numOfCritters Slider - adds number to end of slider
		final Label numCrit = new Label("1");
		critSlider.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				numCrit.textProperty().setValue(String.valueOf((int) critSlider.getValue()));
			}
		});

		// If make button is pressed, makes x amount of critters
		makeButton.setOnAction(e -> {
			int numCritters = (int) critSlider.getValue();
			for(int count = 0; count < numCritters; count++) {
				try {
					Critter.makeCritter(listOfCritters.getValue());
				}
				catch (InvalidCritterException ice) {
					errorInit("ERROR: Invalid Critter");
				}
			}
			Critter.displayWorld(world);
		});


		// add all buttons to pane and sets positioning
		GridPane.setMargin(listOfCritters, new Insets(5, 5, 5, 5));
		GridPane.setMargin(critLabel, new Insets(5, 5, 0, 5));
		GridPane.setMargin(critSlider, new Insets(0, 5, 10, 5));
		GridPane.setMargin(numCrit, new Insets(0, 0, 10, 0));
		GridPane.setMargin(makeButton, new Insets(5, 5, 5, 10));
		GridPane.setConstraints(makeButton, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);

		paneController.add(numCrit, 1, 2);
		paneController.add(critSlider, 0, 2);
		paneController.add(makeButton, 0, 0);
		paneController.add(critLabel, 0, 1);
		paneController.add(listOfCritters, 0, 0);
	}

	/** Takes in and configures a combo box
	 * @param list Combo box
	 * @return Returns configured combo box with proper drop down list
	 * @throws ClassNotFoundException
	 * @throws URISyntaxException
	 */
	private ComboBox<String> comboConfig(ComboBox<String> list) throws ClassNotFoundException, URISyntaxException {
		ObservableList<String> oList = FXCollections.observableArrayList(listOfCritters());
		list = new ComboBox<String>(oList);
		list.setValue("Algae");			// default to prompt user
		return list;
	}

	/**
	 * @return Returns list of critters that needs to be placed in combo box
	 * @throws URISyntaxException
	 * @throws ClassNotFoundException
	 */
	private static ArrayList<String> listOfCritters() throws URISyntaxException, ClassNotFoundException {
		// Creates an array list to be returned containing all Critter names
		ArrayList<String> allCritters = new ArrayList<String>();

		// Creates file to access to get all critter names
		File directory = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String pkg = Main.class.getPackage().toString().split(" ")[1];
		directory = new File(directory.getAbsolutePath() + "/" + pkg);
		String [] lfile = directory.list();

		// Finding class names in file
		for (int i = 0;i < lfile.length; i++) {
			if(!(lfile[i].substring(lfile[i].length() - 6, lfile[i].length()).equals(".class"))) { continue; }
			if((Class.forName(pkg + ".Critter").isAssignableFrom(Class.forName(pkg + "." + lfile[i].substring(0, lfile[i].length() - 6))))
					&& !(Modifier.isAbstract(Class.forName(pkg + "." + lfile[i].substring(0, lfile[i].length() - 6)).getModifiers()))) {
				allCritters.add(lfile[i].substring(0, lfile[i].length() - 6));
			}
		}

		return allCritters;
	}

	/** Initializes seed button and seed text field
	 */
	private static void seedInit() {

		seedButton.setOnAction(e -> {
			try{
				int seed = Integer.parseInt(seedText.getText());
				Critter.setSeed(seed);
			}
			catch (NumberFormatException nfe){
				errorInit("ERROR: Not a valid number");
			}

		});

		// add all buttons to pane and sets positioning
		GridPane.setConstraints(seedButton, 1, 7, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setMargin(seedButton, new Insets(0, 5, 15, 5));
		GridPane.setMargin(seedText, new Insets(15, 5, 5, 5));
		seedText.setPrefWidth(10);

		paneController.add(seedButton,0, 8);
		paneController.add(seedText, 0, 7);
	}

	/** Initializes run stats button and combo box.
	 *  Run stats shows how many critters are present in world.
	 */
	private void statsInit() {
		//disables stop button
		stopStatsButton.setDisable(true);

		// configures combo box
		try {
			runStatsComboBox = comboConfig(runStatsComboBox);
		} catch (ClassNotFoundException cnfe) {
			errorInit("ERROR: Class not found");
		} catch (URISyntaxException use) {
			errorInit("ERROR: Syntax Exception");
		}

		// if run stats button is pressed, invoke run stats
		runStatsButton.setOnAction(e -> {
			isStatsRunning = true;
			runStatsButton.setDisable(true);
			stopStatsButton.setDisable(false);
			runStatsComboBox.setDisable(true);

			// Animation timer looping run stats to show output continuously
			AnimationTimer statsRun = new AnimationTimer() {
				@Override
				public void handle(long now) {

					// if not running stats, exit
					if(!isStatsRunning) {
						this.stop();
					}
					// if running stats, output
					else {

						try {
							List<Critter> instances = new ArrayList<Critter>();
							instances = Critter.getInstances(runStatsComboBox.getValue());
							Class<?> c = Class.forName(myPackage + "." + runStatsComboBox.getValue());
							Method stats = c.getMethod("runStats", java.util.List.class);
							String statsMessage = (String) stats.invoke(c, instances);

							statsWindow.textProperty().setValue(statsMessage);
						} catch (Exception ice) {
							errorInit("ERROR: Invalid Critter selected");
							//isStatsRunning = false;
							//runStatsButton.setDisable(false);
						}



					}
				}
			};
			statsRun.start();
		});

		// if stop stats is pressed, stop run stats
		stopStatsButton.setOnAction(e -> {
			isStatsRunning = false;				// flag to stop run stats
			statsWindow.clear();				// clearing run stats text field
			runStatsButton.setDisable(false);
			stopStatsButton.setDisable(true);
			runStatsComboBox.setDisable(false);
		});

		// add all buttons to pane and sets positioning
		GridPane.setMargin(stopStatsButton, new Insets(5, 5, 5, 5));
		GridPane.setMargin(runStatsButton, new Insets(5, 5, 5, 5));
		GridPane.setMargin(runStatsComboBox, new Insets(5, 5, 5, 5));
		GridPane.setMargin(statsWindow, new Insets(0, 5, 15, 5));
		GridPane.setConstraints(runStatsButton, 0, 10, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(stopStatsButton, 0, 10, 1, 1, HPos.RIGHT, VPos.CENTER);

		paneController.add(stopStatsButton, 0, 10);
		paneController.add(runStatsComboBox, 0, 10);
		paneController.add(runStatsButton, 0, 10);
		paneController.add(statsWindow, 0,11);
	}

	/** Initializes step buttons and slider
	 *  User can choose how many world time steps to implement
	 */
	private static void stepInit() {

		// Initializes slider and adds # to side of slider
		final Label numberSteps = new Label("1");
		stepSlider.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				numberSteps.textProperty().setValue(String.valueOf((int) stepSlider.getValue()));
			}
		});

		// If step button is pressed, x amount of world time steps will take place
		stepButton.setOnAction(e -> {
			int numSteps = (int) stepSlider.getValue();
			for(int count = 0; count < numSteps; count++) {
				Critter.worldTimeStep();
			}

			// clears world, but retains grid lines
			world.getChildren().retainAll(world.getChildren().get(0));
			Critter.displayWorld(world);
		});

		// add all buttons to pane and sets positioning
		GridPane.setMargin(stepLabel, new Insets(10, 5, 0, 5));
		GridPane.setMargin(stepSlider, new Insets(0, 5, 0, 5));
		GridPane.setMargin(numberSteps, new Insets(0, 0, 0, 0));
		GridPane.setMargin(stepButton, new Insets(0, 5, 10, 5));
		GridPane.setConstraints(stepButton, 0, 4, 1, 1, HPos.CENTER, VPos.CENTER);

		paneController.add(stepLabel, 0, 3);
		paneController.add(numberSteps, 1, 4);
		paneController.add(stepSlider, 0, 4);
		paneController.add(stepButton, 0, 5);
	}

	/** Initializes animation buttons and slider
	 */
	private static void aniInit() {

		// if animation button is pressed, continuously run world time steps
		animateButton.setOnAction(e -> {
			// if animating, disable buttons and "animate" by running world time
			if(!isAnimating){
				isAnimating = true;
				disableButtons();

				// Animation timer used to continuously run world
				AnimationTimer run = new AnimationTimer() {
					@Override
					public void handle(long now) {
						// If not animating, stop
						if(!isAnimating){
							this.stop();
						}
						// Run animation at given speeds, depending on user input
						else {

							try {
								// configuring speed of animation by adding delays
								int timeSteps = (int) animationSpeed.getValue();
								timeSteps = (timeSteps - 201)*(-1);
								for(int i = 0; i < timeSteps; i++) {
									Critter.worldTimeStep();
									world.getChildren().retainAll(world.getChildren().get(0));
									Critter.displayWorld(world);
								}
							}
							catch (Exception e1) {
								errorInit("Error!");
							}
						}
					}
				};
				run.start();
			}
		});

		// if pause button pressed, stop animating and enable all buttons
		pauseButton.setOnAction(e -> {
			enableButtons();
			isAnimating = false;
		});

		// add all buttons to pane and sets positioning
		paneController.setConstraints(animateButton, 0, 20, 1, 1, HPos.LEFT, VPos.CENTER); //I want to set
		paneController.setConstraints(pauseButton, 0, 20, 1, 1, HPos.RIGHT, VPos.CENTER); //I want to set
		GridPane.setMargin(animateLabel, new Insets(10, 5, 0, 5));
		GridPane.setMargin(animateButton, new Insets(2, 10, 0, 5));
		GridPane.setMargin(pauseButton, new Insets(2, 5, 0, 10));
		GridPane.setMargin(animationSpeed, new Insets(2, 5, 0, 5));

		paneController.add(animateLabel, 0, 18);
		paneController.add(animateButton, 0, 20);
		paneController.add(pauseButton, 0, 20);
		paneController.add(animationSpeed, 0, 19);
	}

	/** Initializes error window. If error occurs, window will pop up.
	 * @param message Message to be displayed on window
	 */
	private static void errorInit(String message){
		alert.setMinWidth(200);

		Label label = new Label();
		label.setText(message);

		// If button is pressed, window will close
		errorButton.setOnAction(e ->{
			alert.close();
		});

		// configuring error box
		VBox errorBox = new VBox(10);
		errorBox.getChildren().addAll(label, errorButton);
		errorBox.setAlignment(Pos.CENTER);

		alert.setScene(new Scene(errorBox));
		alert.showAndWait();
	}

	/** Disables all buttons during animation
	 *  Can only click quit, pause, and run stats
	 */
	private static void disableButtons(){
		stepButton.setDisable(true);
		seedButton.setDisable(true);
		animateButton.setDisable(true);
		stepSlider.setDisable(true);
	}

	/** Enables all buttons during animation
	 */
	private static void enableButtons(){
		stepButton.setDisable(false);
		seedButton.setDisable(false);
		animateButton.setDisable(false);
		stepSlider.setDisable(false);
	}

}
