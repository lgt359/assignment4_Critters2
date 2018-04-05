package assignment5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javax.swing.*;
import javax.xml.soap.Text;
import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main extends Application {

	private static String myPackage;
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	static Stage controlStage = new Stage();
	static GridPane paneController = new GridPane();
	static GridPane world = new GridPane();
	static GridPane scene = new GridPane();

	// Make Critters
	static ComboBox<String> listOfCritters = new ComboBox<>();
	static Button makeButton = new Button("Make");
	static Label critLabel = new Label("Number of Critters");
	static Slider critSlider = new Slider(0, 100, 1);

	// Quit
	static Button quitButton = new Button("Quit");

	// Stats
	static TextArea statsWindow = new TextArea();
	static Button runStatsButton = new Button("Stats");

	// Step
	static Label stepLabel = new Label("Number of Steps");
	static Button stepButton = new Button("Step");
	static Slider numOfSteps = new Slider(0, 100, 1);

	// Seed
	static TextArea seedText = new TextArea();
	static Button seedButton = new Button("Set Seed");

	// Animation
	static Button animateButton = new Button("Run");
	static Button pauseButton = new Button("Stop");
	static Label animateLabel = new Label("Speed of Animation");
	static Slider animationSpeed = new Slider(0, 50, 1);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException{
		primaryStage.setTitle("Critters GUI");

		// Initialization
		worldInit();
		makeInit();
		//exitInit();
		//seedInit();
		//statsInit();
		//stepInit();
		//aniInit();

		primaryStage.setScene(new Scene(world, 1000, 1000));
		primaryStage.show();

		controlStage.setScene(new Scene(paneController, 100, 100));
		controlStage.show();
	}


	private static void worldInit() {
		world.setGridLinesVisible(true);

		for(int i = 0; i < Params.world_width; i++) {
			ColumnConstraints colC = new ColumnConstraints();
			colC.setPercentWidth(100.0 / Params.world_width);
			colC.setHalignment(HPos.CENTER);
			world.getColumnConstraints().add(colC);
		}

		for(int j = 0; j < Params.world_height; j++) {
			RowConstraints rowC = new RowConstraints();
			rowC.setPercentHeight(100.0 / Params.world_height);
			rowC.setValignment(VPos.CENTER);
			world.getRowConstraints().add(rowC);
		}

	}

	private static void exitInit() {
		quitButton.setOnAction(e -> {
			System.exit(0);
		});

		paneController.add(quitButton, 0, 12);
	}

	private void makeInit() {

	    /* Initialize all 4 elements */

		// 1. listOfCritters Combo Box
		try {
			comboConfig();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Class Not Found Exception");
		} catch (URISyntaxException use) {
			System.out.println("URI Syntax Exception");
		}

		// 2. Label
		// need to put in proper place

		// 3. numOfCritters Slider
		// need to add # to thumb and put in proper place
		final Label numCrit = new Label("0");
		critSlider.valueProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				numCrit.textProperty().setValue(String.valueOf((int) critSlider.getValue()));
			}
		});

		// 4. Make Button
		makeButton.setOnAction(e -> {
			int numCritters = (int) critSlider.getValue();
			for(int count = 0; count < numCritters; count++) {
				try {
					Critter.makeCritter(listOfCritters.getValue());
				}
				catch (InvalidCritterException ice) {
					System.out.println("Invalid Critter");
				}
			}

			// CRITTER DISPLAY WORLD ?????????? we created the critters now we need to add them to the animation

		});


		// add all buttons to pain
		paneController.add(listOfCritters, 0, 0);
		paneController.add(critLabel, 0, 1);
		paneController.add(numCrit, 1, 2);
		paneController.add(critSlider, 0, 2);
		paneController.add(makeButton, 0, 4);
	}

	private void comboConfig() throws ClassNotFoundException, URISyntaxException {
		ObservableList<String> oList = FXCollections.observableArrayList(listOfCritters());
		listOfCritters = new ComboBox<String>(oList);
		listOfCritters.setValue("Select Critter:");
	}

	private static ArrayList<String> listOfCritters() throws URISyntaxException, ClassNotFoundException {
		ArrayList<String> allcrits = new ArrayList<String>();
		File directory = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String pkg = Main.class.getPackage().toString().split(" ")[1];
		directory = new File(directory.getAbsolutePath() + "/" + pkg);
		String [] lfile = directory.list();
		for (int i = 0;i < lfile.length; i++) {
			if(!(lfile[i].substring(lfile[i].length() - 6, lfile[i].length()).equals(".class"))) { continue; }
			if((Class.forName(pkg + ".Critter").isAssignableFrom(Class.forName(pkg + "." + lfile[i].substring(0, lfile[i].length() - 6))))
					&& !(Modifier.isAbstract(Class.forName(pkg + "." + lfile[i].substring(0, lfile[i].length() - 6)).getModifiers()))) {
				allcrits.add(lfile[i].substring(0, lfile[i].length() - 6));
			}
		}
		return allcrits;
	}

	public static void seedInit() {
		paneController.add(seedButton,0, 10);
		paneController.add(seedText, 0, 7);
	}

	public static void statsInit() {
		paneController.add(runStatsButton, 0, 9);
		paneController.add(statsWindow, 0,10);
	}

	public static void stepInit() {
		paneController.add(stepButton, 0, 12);
		paneController.add(numOfSteps, 0, 13);

		stepButton.setOnAction(e -> {
			int numSteps = (int) numOfSteps.getValue();
			for(int count = 0; count < numSteps; count++) {
				Critter.worldTimeStep();
			}

			// CRITTER DISPLAY WORLD ?????????? we've done this many steps now we need to do show

		});
	}

	public static void aniInit() {
		paneController.add(animateButton, 0, 15);
		paneController.add(pauseButton, 0, 16);
		paneController.add(animationSpeed, 0, 17);
	}


}
