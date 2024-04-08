package engine;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class AlertBox {
	public static void display(String title,String message){
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(400);
		Label l = new Label();
		l.setText(message);
		Button close = new Button("Close the window");
		close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				window.close();
			}});
		 VBox pane = new VBox(10);
		 pane.getChildren().addAll(l, close);
		 pane.setAlignment(Pos.CENTER); 
		 Scene scene = new Scene(pane); 
		 window.setScene(scene);
		 window.showAndWait();
	}
	public static void display2(String title,String message){
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(400);
		Label l = new Label();
		l.setText(message);
		Button close = new Button("Close the Game");
		close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				window.close();
			}});
		 VBox pane = new VBox(10);
		 pane.getChildren().addAll(l, close);
		 pane.setAlignment(Pos.CENTER); 
		 Scene scene = new Scene(pane); 
		 window.alwaysOnTopProperty();
		 window.setScene(scene);
		 window.showAndWait();
	}
	
	
	
	
}
