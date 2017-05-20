 package controller;
	
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	Stage specificationStage;
	Stage primaryStage;
	//private Image image;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		mainWindow();
	}
	
	public void mainWindow(){
		try {
			
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindowView.fxml"));
			AnchorPane pane = loader.load();
			
			MainWindowController controller = loader.getController();
			controller.setMain(this, primaryStage);
			
			Scene scene = new Scene(pane);
			
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("file:cmu-logo.jpg"));
			
			primaryStage.setTitle("PDExpress");
			primaryStage.show();
			
		} catch (IOException e) {
			//e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Form Loading Error");
			alert.setHeaderText("Inferface will not load");
			alert.setContentText(e.toString());
			alert.show();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
