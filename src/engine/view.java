package engine;

import java.util.ArrayList;

import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;

public class view extends Application {

	static GridPane game = new GridPane();
	static Hero hero;
	static VBox heroPanel = new VBox();
	static VBox allHeroPanel = new VBox();
	static VBox instructions = new VBox();
	static GridPane controls = new GridPane();
	static String select = "";
	static Stage stage;
	static Button [][] list = new Button[15][15];
	
	
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setMaximized(true);
		// primaryStage.initStyle(StageStyle.UNDECORATED);
		stage = primaryStage;
		primaryStage.setTitle("Game");
		String s = new String(
				"C:\\Users\\MY lap\\OneDrive\\Desktop\\Milestone2-Solution\\Heroes.csv");
		Game.loadHeroes(s);
		ArrayList<Hero> heroes = Game.availableHeroes;
		startingHero(primaryStage,heroes);
		
		
	}
	
	public static void updateMap(GridPane game){
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Button b = list[i][j];
				if (Game.map[i][j] instanceof CharacterCell) {
					if (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Hero){
						b.setText(""+ ((CharacterCell) Game.map[i][j]).getCharacter().getName());
						b.setId("Hero");
					}
					if (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Zombie)
						b.setText("Zombie");
					if (((CharacterCell) Game.map[i][j]).getCharacter() ==null)
						b.setText("");
				}
				if (Game.map[i][j] instanceof CollectibleCell) {
					if (((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Vaccine)
						b.setText("Vaccine");
					else
						b.setText("Supply");
				}
				if(!Game.map[i][j].isVisible())
					b.setVisible(false);
				if(Game.map[i][j].isVisible())
					b.setVisible(true);
				
				
				b.setMinWidth(100);
				b.setMaxWidth(100);}}
		Button b = list[0][0];
			
		if(Game.map[0][0].isVisible())
			b.setVisible(true);
			else
				b.setVisible(false);
	}
	
	
	public static Pane Lost()
	{
		Label l = new Label("You lost");
		Button b =new Button("close the game");
		b.setOnAction(e->{
			stage.close();
		});
		VBox v = new VBox(l,b);
		return v;
	}
	
	
	
	public static void makeMap(GridPane game) {
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Button b = new Button();
				//b.setMinWidth(90);
				
				if (Game.map[i][j] instanceof CharacterCell) {
					if (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Hero){
						b.setText(""+ ((CharacterCell) Game.map[i][j]).getCharacter().getName());
						b.setId("Hero");
				}
					if (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Zombie)

						b = new Button("Zombie");
				}
				if (Game.map[i][j] instanceof CollectibleCell) {
					if (((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Vaccine)
						b = new Button("Vaccine");
					else
						b = new Button("Supply");
				}
				if(!Game.map[i][j].isVisible())
					b.setVisible(false);
				if(Game.map[i][j].isVisible())
					b.setVisible(true);
				list[i][j] = b;
				b.setMinWidth(100);
				b.setMaxWidth(100);
				//b.setMinHeight(50);
				
				b.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Button b = (Button) event.getSource(); 
						if(hero == null)
						{
							for (int i = 0; i  < list.length; i++) {
								for (int j = 0; j < list.length; j++) {
									if(b == list[i][j] && Game.map[i][j] instanceof CharacterCell){
										hero = (Hero) ((CharacterCell) Game.map[i][j]).getCharacter();
										heroPanel.getChildren().remove(0);
										setSelectedHeroPanel(hero);
										updateMap(game);
										return;}
								}}
						}
						
						if(hero != null){
								for (int i = 0; i  < list.length; i++) {
									for (int j = 0; j < list.length; j++) {
										if(b == list[i][j] && Game.map[i][j] instanceof CharacterCell){
											hero.setTarget(((CharacterCell) Game.map[i][j]).getCharacter());
											heroPanel.getChildren().remove(0);
											setSelectedHeroPanel(hero);
											updateMap(game);
											return;}
									}}
								}
							
							if(b.getText().equals("Zombie"))
							{
								if(hero != null)
								{
									for (int i = 0; i  < list.length; i++) {
										for (int j = 0; j < list.length; j++) {
											if(b == list[i][j] && Game.map[i][j] instanceof CharacterCell){
												hero.setTarget(((CharacterCell) Game.map[i][j]).getCharacter());
												heroPanel.getChildren().remove(0);
												setSelectedHeroPanel(hero);
												updateMap(game);
												return;}
										}}
								}
									
							}
							
							
							
						
						
						
					}
					}
				);
				game.add(b,j,14-i );
			}
		}

	}

	public static void setAllHeroPanel(VBox allHeroPanel) {
		allHeroPanel.getChildren().clear();
		for (Hero h : Game.heroes) {
			String info = "Name: " + h.getName()+ "\n";
			
			if(h instanceof Fighter)
				info += "Type: Fighter" +"\n";
			if(h instanceof Medic)
			info += "Type: Medic" +"\n";
			if(h instanceof Explorer)
			info += "Type : Explorer" +"\n";
			
			info += "Current Hp: "+ h.getCurrentHp()+ "\n" + "Attack Damage: "+ h.getAttackDmg() + "\n"; 
			info += "Max Action Points : " + h.getMaxActions()+ "\n";
			
			
			allHeroPanel.getChildren().add(new Label(info));
		}
		allHeroPanel.setSpacing(5);
	}
	
	public static void setStartingHeroPanels(VBox allHeroPanel) {
		for (int i = 0; i < Game.availableHeroes.size(); i++) {
			Hero h = Game.availableHeroes.get(i);
			String info = "Name: " + h.getName()+ "\n";
			
			if(h instanceof Fighter)
				info += "Type: Fighter" +"\n";
			if(h instanceof Medic)
			info += "Type: Medic" +"\n";
			if(h instanceof Explorer)
			info += "Type : Explorer" +"\n";
			
			info += "Current Hp: "+ h.getCurrentHp()+ "\n" + "Attack Damage: "+ h.getAttackDmg() + "\n"; 
			info += "Max Action Points : " + h.getMaxActions()+ "\n";
			
			
			allHeroPanel.getChildren().add(new Label(info));
		}
		allHeroPanel.setSpacing(10);
	}
	public static void setPanel(Hero h, VBox heroPanel) {
		String info = "Name: " + h.getName()+ "\n";
		
		if(h instanceof Fighter)
			info += " Type: Fighter" +"\n";
		if(h instanceof Medic)
		info += " Type: Medic" +"\n";
		else
		info += " Type : Explorer" +"\n";
		
		info += "Current Hp: "+ h.getCurrentHp() + " Attack Damage: "+ h.getAttackDmg() + "\n"; 
		info += "Max Action Points : " + h.getMaxActions();
		
		Label i = new Label(info);
		allHeroPanel.getChildren().add(i);

	}

	public static  void setSelectedHeroPanel(Hero h) {
		if(h == null)
		{
			Label l = new Label("No Hero Selected");
			heroPanel.getChildren().add(l);
			return;
		}
		String info = "Selected Hero : " + "\n" + 
				"Name: " + h.getName() + "\n";
		
		if(h instanceof Fighter)
			info += "Type: Fighter" +"\n";
		if(h instanceof Medic)
		info += "Type: Medic" +"\n";
		if(h instanceof Explorer)
		info += "Type : Explorer" +"\n";
		
		info += "Current Hp: "+ h.getCurrentHp() + "\n"
				+ "Attack Damage: " + h.getAttackDmg() + "\n"; 
		
		info += "Actions Points : " + h.getActionsAvailable() +"\n";
		
		info += "Supplies : " + h.getSupplyInventory().size() +"\n"+ 
				"Vaccines : " + h.getVaccineInventory().size() + "\n";
		if((h.getTarget()!=null))
			info += "Current Target: "+ h.getTarget().getName();
		else
			info += "Current Target: No Target";
		Label i = new Label(info);
		heroPanel.getChildren().add(i);

	}
	public static void setSelect(String s){
		
		select = s;
	}
	public static void setHero(String s){
		
		for(int i = 0; i<Game.availableHeroes.size();i++){
			if(s.equals(Game.availableHeroes.get(i).getName()))
				hero = Game.availableHeroes.get(i);
		}
	}
	
	public static void startingHero(Stage primaryStage, ArrayList<Hero> heroes) {

		VBox selectHero1 = new VBox();
		selectHero1.setSpacing(90);
		Button btn = new Button();
		ArrayList<Button> b = new ArrayList<Button>();
		for (int i = 0; i < heroes.size(); i++) {
				btn = new Button( heroes.get(i).getName());
			
			selectHero1.getChildren().addAll(btn);
			b.add(btn);
			btn.setId(heroes.get(i).getName());
			btn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Button clickedButton = (Button) event.getSource();
					 setSelect(clickedButton.getId());
					 primaryStage.close();
					 	
						setHero(select);
						Hero h = hero;
						Game.startGame(h);

						
						BorderPane allPanels = new BorderPane();

						allPanels.setCenter(game);
						
						VBox left = new VBox();
						makeMap(game);
						setAllHeroPanel(allHeroPanel);
						setSelectedHeroPanel(h);
						setInstructions(instructions);
						setControls(controls);
						
						left.getChildren().addAll(allHeroPanel,heroPanel);
						left.setSpacing(10);
						left.setMinWidth(300);
						controls.add(instructions,9,0);
						allPanels.setLeft(left);
						allPanels.setBottom(controls);
						//allPanels.setRight(instructions);
						Scene mainScene = new Scene(allPanels, 1000, 600);
						primaryStage.setScene(mainScene);
						primaryStage.show();
					;
				}
			});
		}
		VBox n = new VBox();
		setStartingHeroPanels(n);
		GridPane x = new GridPane();
		x.add(selectHero1,1,0);
		x.add(n,0,0);
		Scene selectHero = new Scene(x, 400, 200);
		primaryStage.setScene(selectHero);
		primaryStage.show();
	}
	
	public static void setInstructions(VBox instructions)
	{
		String inst = "Instructions : " + "\n" + 
					"1- To select a Hero click on the hero. " + "\n"+
					"2- If you have a Hero selected and you wish to select another you must unselect the previous Hero first. " + "\n"+
						"3- To target a Zombie click on zombie" + "\n" +
						"4- To move click on the move buttons";
		Label i = new Label(inst);
		instructions.getChildren().add(i);
	}
	
	public static void setControls(GridPane controls)
	{
		Button attack = new Button("Attack");
		Button up = new Button("UP");
		Button down = new Button("Down");
		Button right = new Button("RIGHT");
		Button left = new Button("LEFT");
		Button special = new Button("Use Special");
		Button end = new Button("End Turn");
		Button cure = new Button("Cure");
		//Button Quit = new Button("Quit");
		Button noHero = new Button("Unselect");
		Button Ai = new Button("Let the AI play");
		
		Ai.setOnAction(new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent event) {
						try {
							Game.artificialIntelligence(hero);
						} catch (MovementException e) {
							// TODO Auto-generated catch block
							AlertBox.display("Exception",e.getMessage());
						} catch (NotEnoughActionsException e) {
							// TODO Auto-generated catch block
							AlertBox.display("Exception",e.getMessage());
						} catch (InvalidTargetException e) {
							// TODO Auto-generated catch block
							AlertBox.display("Exception",e.getMessage());;
						} catch (NoAvailableResourcesException e) {
							// TODO Auto-generated catch block
							AlertBox.display("Exception",e.getMessage());
						}
						
						
						
					}});
		noHero.setOnAction(new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						hero = null;
						heroPanel.getChildren().remove(0);
						setSelectedHeroPanel(hero);
					}
			
				}
				
			
				
				);
		attack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					hero.attack();
				} catch (NotEnoughActionsException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				} catch (InvalidTargetException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				}
				finally {
				heroPanel.getChildren().remove(0);
				updateMap(game);
				setAllHeroPanel(allHeroPanel);
				setSelectedHeroPanel(hero);
			}}
			}
		);
		end.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Game.endTurn();
					
				} catch (NotEnoughActionsException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				} catch (InvalidTargetException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				}
				
				if(Game.checkWin()){
					AlertBox.display2("Status","You Won!");
					stage.close();
					
				}
				else
				{
					if(Game.checkGameOver())
				if(Game.checkGameOver()){
					AlertBox.display2("Status","You Lost!");
					stage.close();
					
				}
				}
				
			
				heroPanel.getChildren().remove(0);
				updateMap(game);
				setAllHeroPanel(allHeroPanel);
				setSelectedHeroPanel(hero);
				
			}
			
			}
		);
		special.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					hero.useSpecial();
				} catch (NoAvailableResourcesException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				} catch (InvalidTargetException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				}
				heroPanel.getChildren().remove(0);
				updateMap(game);
				setAllHeroPanel(allHeroPanel);
				setSelectedHeroPanel(hero);
				
			}
			}
		);
		cure.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					hero.cure();
				} catch (NoAvailableResourcesException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				} catch (InvalidTargetException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				} catch (NotEnoughActionsException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				}
				updateMap(game);
				setAllHeroPanel(allHeroPanel);
				if(Game.checkWin()){
					AlertBox.display2("Status","You Won!");
					stage.close();
					
				}
				else
				{
					if(Game.checkGameOver())
				if(Game.checkGameOver()){
					AlertBox.display2("Status","You Lost!");
					stage.close();
					
				}
				}
			}
			}
		);
		up.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					hero.move(Direction.UP);
				} catch (MovementException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				} catch (NotEnoughActionsException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				}
				if(Game.checkWin()){
					AlertBox.display2("Status","You Won!");
					stage.close();
					
				}
				else
				{
					if(Game.checkGameOver())
				if(Game.checkGameOver()){
					AlertBox.display2("Status","You Lost!");
					stage.close();
					
				}
				}
				heroPanel.getChildren().remove(0);
				updateMap(game);
				setAllHeroPanel(allHeroPanel);
				setSelectedHeroPanel(hero);
			}
			}
		);
		down.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					hero.move(Direction.DOWN);
				} catch (MovementException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());;
				} catch (NotEnoughActionsException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				}
				if(Game.checkWin()){
					AlertBox.display2("Status","You Won!");
					stage.close();
					
				}
				else
				{
					if(Game.checkGameOver())
				if(Game.checkGameOver()){
					AlertBox.display2("Status","You Lost!");
					stage.close();
					
				}
				}
				heroPanel.getChildren().remove(0);
				updateMap(game);
				setAllHeroPanel(allHeroPanel);
				setSelectedHeroPanel(hero);
			}
			}
		);
		right.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					hero.move(Direction.RIGHT);
				} catch (MovementException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				} catch (NotEnoughActionsException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				}
				if(Game.checkWin()){
					AlertBox.display2("Status","You Won!");
					stage.close();
					
				}
				else
				{
					if(Game.checkGameOver())
				if(Game.checkGameOver()){
					AlertBox.display2("Status","You Lost!");
					stage.close();
					
				}
				}
			heroPanel.getChildren().remove(0);
			updateMap(game);
			setAllHeroPanel(allHeroPanel);
			setSelectedHeroPanel(hero);
			}
			}
		);
		left.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					hero.move(Direction.LEFT);
				} catch (MovementException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				} catch (NotEnoughActionsException e) {
					// TODO Auto-generated catch block
					AlertBox.display("Exception",e.getMessage());
				}
				if(Game.checkWin()){
					AlertBox.display2("Status","You Won!");
					stage.close();
					
				}
				else
				{
					if(Game.checkGameOver())
				if(Game.checkGameOver()){
					AlertBox.display2("Status","You Lost!");
					stage.close();
					
				}
				}
				heroPanel.getChildren().remove(0);
				updateMap(game);
				setAllHeroPanel(allHeroPanel);
				setSelectedHeroPanel(hero);
			}
			}
		);
		//Quit.setOnAction(e->{
		//	stage.close();
		//});
		
		
		controls.add(attack, 5, 0);
		controls.add(up, 1, 0);
		controls.add(down, 2, 0);
		controls.add(right, 4, 0);
		controls.add(left, 3, 0);
		controls.add(special, 0, 0);
		controls.add(end, 8, 0);
		controls.add(cure, 6, 0);
		controls.add(noHero, 7, 0);
		controls.add(Ai, 3, 1);



		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		launch(args); // Step 5

	}

}