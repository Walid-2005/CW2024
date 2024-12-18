package com.example.demo.controller;

import java.lang.reflect.Constructor; //https://www.programiz.com/java-programming/reflection
import java.lang.reflect.InvocationTargetException; //In Java, reflection allows us to inspect and manipulate classes, interfaces, constructors, methods, and fields at run time.
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();

	}

	// @Override
	// public void update(Observable arg0, Object arg1) {
	// 	try {
	// 		goToLevel((String) arg1);
	// 	} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
	// 			| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	// 		Alert alert = new Alert(AlertType.ERROR);
	// 		alert.setContentText(e.getClass().toString());
	// 		alert.show();
	// 	}
	// }

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// Display error with more details
			Alert alert = new Alert(Alert.AlertType.ERROR);
			Throwable cause = e.getCause();
			alert.setContentText(cause != null ? cause.toString() : e.toString());
			alert.show();
			
			// Print stack trace for detailed debugging
			if (cause != null) {
				cause.printStackTrace();
			} else {
				e.printStackTrace();
			}
		}
	}


}
