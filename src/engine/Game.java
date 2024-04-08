package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

public class Game {

	public static ArrayList<Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static ArrayList<String> zo = new ArrayList<>();
	public static Cell[][] map = new Cell[15][15];
	public static Point [] vaccinesPoint = new Point[5];
	public static int[] distance = new int[5];
	public static boolean Upflag;
	public static boolean Downflag;
	public static boolean Leftflag;
	public static boolean Rightflag;
	public static boolean vaccineFlag;
	public static int multiplezombies=0;
	public static void loadHeroes(String filePath) throws IOException {
		availableHeroes = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] sp = line.split(",");
			Hero h;
			if (sp[1].equals("EXP")) {
				h = new Explorer(sp[0], Integer.parseInt(sp[2]), Integer.parseInt(sp[4]), Integer.parseInt(sp[3]));
			} else if (sp[1].equals("FIGH")) {
				h = new Fighter(sp[0], Integer.parseInt(sp[2]), Integer.parseInt(sp[4]), Integer.parseInt(sp[3]));
			} else {
				h = new Medic(sp[0], Integer.parseInt(sp[2]), Integer.parseInt(sp[4]), Integer.parseInt(sp[3]));
			}
			availableHeroes.add(h);
			line = br.readLine();
		}
		br.close();
	}
	public static void updateDistance(Hero h) {
		Point heroLocation = h.getLocation();
		int res;
		for (int i = 0; i < vaccinesPoint.length; i++) {
			res = Math.abs(heroLocation.x - vaccinesPoint[i].x ) + Math.abs(heroLocation.y - vaccinesPoint[i].y );
			distance[i] = res;
		}

	}
	public static Point closeVaccine(Hero h) {
		updateDistance(h);
		int minValue = distance[0];
		int minIndex = 0;

		for (int i = 1; i < distance.length; i++) {
			int currentNumber = distance[i];
			if (currentNumber < minValue) {
				minValue = currentNumber;
				minIndex = i;
			}
		}
		return vaccinesPoint[minIndex];


	}
	public static Point searchVaccine(Hero h){
		Point heroLocation = h.getLocation();




		return heroLocation;



	}

	public static void multiplezombies(Hero h) throws MovementException, NotEnoughActionsException {
		System.out.println("multiple zombiess "+ multiplezombies);
		System.out.println("zoo arraylist " + zo.toString());
		//check adjacent cells
		if(!zo.isEmpty()) {
			if(zo.contains("up")) {
				//check if up right is empty as well as it's right
				if(map[h.getLocation().x + 1][h.getLocation().y+1] instanceof CharacterCell && map[h.getLocation().x][h.getLocation().y+1] instanceof CharacterCell){
					if(((CharacterCell) map[h.getLocation().x + 1][h.getLocation().y+1]).getCharacter() == null && ((CharacterCell) map[h.getLocation().x][h.getLocation().y+1]).getCharacter() == null){
						h.move(Direction.RIGHT);
						h.move(Direction.UP);
						System.out.println("righttup");

					}

				}
				else
				{
					h.move(Direction.RIGHT);
					h.move(Direction.UP);
					System.out.println("righttupvacm");
				}

				//check if up left is empty as well as it's left
				if(map[h.getLocation().x + 1][h.getLocation().y-1] instanceof CharacterCell && map[h.getLocation().x][h.getLocation().y-1] instanceof CharacterCell){
					if(((CharacterCell) map[h.getLocation().x + 1][h.getLocation().y-1]).getCharacter() == null && ((CharacterCell) map[h.getLocation().x][h.getLocation().y-1]).getCharacter() == null){
						h.move(Direction.LEFT);
						h.move(Direction.UP);
						System.out.println("lefftttup");
					}

				}	
				else
				{
					h.move(Direction.LEFT);
					h.move(Direction.UP);
					System.out.println("lefftttupvacm");
				}

			}
			else if (zo.contains("down")) {
				//check if down right is empty as well as it's right
				if(map[h.getLocation().x -1][h.getLocation().y+1] instanceof CharacterCell && map[h.getLocation().x][h.getLocation().y+1] instanceof CharacterCell){
					if(((CharacterCell) map[h.getLocation().x - 1][h.getLocation().y+1]).getCharacter() == null && ((CharacterCell) map[h.getLocation().x][h.getLocation().y+1]).getCharacter() == null){
						h.move(Direction.RIGHT);
						h.move(Direction.DOWN);
						System.out.println("righttdownn");
					}

				}
				else
				{
					h.move(Direction.RIGHT);
					h.move(Direction.DOWN);
					System.out.println("righttdownnvac");
				}

				//check if up left is empty as well as it's left
				if(map[h.getLocation().x -1][h.getLocation().y-1] instanceof CharacterCell && map[h.getLocation().x][h.getLocation().y-1] instanceof CharacterCell){
					if(((CharacterCell) map[h.getLocation().x -1][h.getLocation().y-1]).getCharacter() == null && ((CharacterCell) map[h.getLocation().x][h.getLocation().y-1]).getCharacter() == null){
						h.move(Direction.LEFT);
						h.move(Direction.DOWN);
						System.out.println("leftdown");
					}

				}
				else
				{
					h.move(Direction.LEFT);
					h.move(Direction.DOWN);
					System.out.println("leftdownvac");
				}
				
				
				
			}

			//			if(!zo.contains("up")) 
			//				h.move(Direction.UP);
			//			
			//			else if(!zo.contains("down")) 
			//				h.move(Direction.DOWN);
			//			
			//			else if(!zo.contains("right")) 
			//				h.move(Direction.RIGHT);
			//			else if (!zo.contains("left")) 
			//				h.move(Direction.LEFT);

		}
	}
	public static Point searchZombie(Hero h){
		Point heroLocation = h.getLocation();
		int minValue = Math.abs(zombies.get(0).getLocation().x - heroLocation.x) + Math.abs(zombies.get(0).getLocation().y - heroLocation.y);
		int minIndex = 0;

		for (int i = 1; i < zombies.size(); i++) {
			int currentNumber = Math.abs(zombies.get(i).getLocation().x - heroLocation.x) + Math.abs(zombies.get(i).getLocation().y - heroLocation.y);;
			if (currentNumber < minValue) {
				minValue = currentNumber;
				minIndex = i;
			}
		}
		return zombies.get(minIndex).getLocation();


	}
	public static void goInXDirection(Hero h, Point p) throws MovementException, NotEnoughActionsException{
		int xv = p.x - h.getLocation().x;
		if(xv==0 && Rightflag){
			if(h.getLocation().x !=14 )
				xv = -1;
			else
				xv =1;
			Rightflag = false;
		}

		if(xv==0 && Leftflag){
			if(h.getLocation().x !=14 )
				xv = -1;
			else
				xv =1;
			Leftflag = false;
		}

		if(xv>0){
			if(map[h.getLocation().x + 1][h.getLocation().y] instanceof CharacterCell ){
				if(((CharacterCell) map[h.getLocation().x + 1][h.getLocation().y]).getCharacter() == null){
					h.move(Direction.UP);	
					System.out.println("Up");
					view.updateMap(view.game);
				}
				else{
					//if its a zombie try moving left/right	
					Upflag = true;
					System.out.println("cant go UP direction");
					zo.add("up");
					multiplezombies++;
					if(multiplezombies>1) 
						multiplezombies(h);
					else
						goInYDirection(h,p);

				}
			}
			else{
				h.move(Direction.UP);
				System.out.println("Up");
				view.updateMap(view.game);
			}

		}

		if(xv<0){
			if(map[h.getLocation().x - 1][h.getLocation().y] instanceof CharacterCell ){
				if(((CharacterCell) map[h.getLocation().x - 1][h.getLocation().y]).getCharacter() == null){
					//not zombie
					h.move(Direction.DOWN);	
					System.out.println("Down");
					view.updateMap(view.game);
				}
				else{
					//if its a zombie try moving left/right
					Downflag = true;
					System.out.println("cant go DOWN direction");
					zo.add("down");
					multiplezombies++;
					if(multiplezombies>1) 
						multiplezombies(h);
					else
						goInYDirection(h,p);

				}
			}
			else{
				//down 3adi lw supply/vacc
				h.move(Direction.DOWN);	
				System.out.println("Down");
				view.updateMap(view.game);
			}

		}
	}
	public static void goInYDirection(Hero h,Point p) throws MovementException, NotEnoughActionsException{
		int yv = p.y - h.getLocation().y;
		if(yv==0 && Upflag){
			if(h.getLocation().y !=14 )
				yv = -1;
			else
				yv = 1;
			Upflag = false;
		}



		if(yv==0 && Downflag){
			if(h.getLocation().y !=14)
				yv = -1;
			else
				yv = 1;
			Downflag = false;
		}

		if(yv>0){
			if(map[h.getLocation().x][h.getLocation().y +1] instanceof CharacterCell ){
				if(((CharacterCell) map[h.getLocation().x][h.getLocation().y+1]).getCharacter() == null){
					h.move(Direction.RIGHT);	
					System.out.println("Right");
					view.updateMap(view.game);
				}
				else{
					//if its a zombie try moving UP/DOWN
					Rightflag = true;
					System.out.println("cant go RIGHT direction");
					zo.add("right");
					multiplezombies++;
					if(multiplezombies>1) 
						multiplezombies(h);
					else
						goInXDirection(h,p);

				}
			}
			else{
				h.move(Direction.RIGHT);
				System.out.println("Right");
				view.updateMap(view.game);
			}

		}

		if(yv<0){
			if(map[h.getLocation().x ][h.getLocation().y-1] instanceof CharacterCell ){
				if(((CharacterCell) map[h.getLocation().x ][h.getLocation().y-1]).getCharacter() == null){
					h.move(Direction.LEFT);	
					System.out.println("Left");
					view.updateMap(view.game);
				}
				else{
					//if its a zombie try moving UP/DOWN
					Leftflag = true;
					System.out.println("cant go LEFT direction");
					multiplezombies++;
					zo.add("left");
					if(multiplezombies>1) 
						multiplezombies(h);
					else
						goInXDirection(h,p);

				}
			}
			else{
				h.move(Direction.LEFT);	
				System.out.println("Left");
				view.updateMap(view.game);
			}

		}
	}
	public static boolean checkArrivedZombies(Hero h , Point p) {

		if(h.checkDistance()){
			for (int i = 0; i < zombies.size(); i++) {
				if(p.x == zombies.get(i).getLocation().x && p.y == zombies.get(i).getLocation().y){
					zombies.remove(i);

				}

			}
			return true;
		}

		return false;
	}




	public static boolean checkArrived(Hero h , Point p) {
		if(h.getLocation().x == p.x && h.getLocation().y == p.y){
			for (int i = 0; i < vaccinesPoint.length; i++) {
				if(p.x == vaccinesPoint[i].x && p.y == vaccinesPoint[i].y){
					vaccinesPoint[i] = new Point(1000,1000);
					distance[i] = 2000;
					return true;
				}

			}

		}

		return false;
	}
	public static void autoGetVaccine(Hero h) throws MovementException, NotEnoughActionsException, InvalidTargetException{
		Point closestVaccine = closeVaccine(h);
		//if(h.getActionsAvailable()>0)
		goInYDirection(h,closestVaccine);
		//if(h.getActionsAvailable()>0)
		goInXDirection(h,closestVaccine);

		if(checkArrived(h , closestVaccine))
			closestVaccine = closeVaccine(h);
		System.out.println("" + closestVaccine.x + "   ,  " + closestVaccine.y);


	}
	public static void autoCureZombie(Hero h) throws MovementException, NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException{
		Point close = searchZombie(h);
		h.setTarget(((CharacterCell) map[close.x][close.y]).getCharacter());
		Point closestZombie = new Point(close.x -1, close.y -1); 
		//if(h.getActionsAvailable()>0)
		goInYDirection(h,closestZombie);
		//if(h.getActionsAvailable()>0)
		goInXDirection(h,closestZombie);

		if(checkArrivedZombies(h , close)){
			h.cure();
			h.setTarget(null);
			closestZombie = searchZombie(h);
		}
		System.out.println("z" + closestZombie.x + "   ,  " + closestZombie.y);


	}
	public static void artificialIntelligence(Hero h) throws MovementException, NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException {

		while(h.getVaccineInventory().size()!=5 && !vaccineFlag){
			autoGetVaccine(h);
			System.out.println(h.getVaccineInventory().size() + "");
			if(h.getVaccineInventory().size()==5)
				vaccineFlag = true;
		}
		while(heroes.size()!=6){
			autoCureZombie(h);
		}



	}
	public static void endTurn() throws NotEnoughActionsException, InvalidTargetException {
		for (Zombie zombie : zombies) {
			zombie.attack();
			zombie.setTarget(null);
		}
		spawnNewZombie();
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++)
				map[i][j].setVisible(false);
		for (Hero hero : heroes) {
			hero.setActionsAvailable(hero.getMaxActions());
			hero.setTarget(null);
			hero.setSpecialAction(false);
			adjustVisibility(hero);
		}
	}

	public static void adjustVisibility(Hero h) {
		Point p = h.getLocation();
		for (int i = -1; i <= 1; i++) {
			int cx = p.x + i;
			if (cx >= 0 && cx <= 14) {
				for (int j = -1; j <= 1; j++) {
					int cy = p.y + j;
					if (cy >= 0 && cy <= 14) {
						if (cy >= 0 && cy <= map.length - 1) {
							map[cx][cy].setVisible(true);
						}
					}
				}
			}
		}
	}

	public static void spawnNewZombie() {
		Zombie z = new Zombie();
		zombies.add(z);
		int x, y;
		do {
			x = ((int) (Math.random() * map.length));
			y = ((int) (Math.random() * map[x].length));
		} while ((map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() != null)
				|| (map[x][y] instanceof CollectibleCell) || (map[x][y] instanceof TrapCell));
		z.setLocation(new Point(x, y));
		map[x][y] = new CharacterCell(z);
	}

	public static boolean checkWin() {
		int remainingVaccines = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] instanceof CollectibleCell
						&& ((CollectibleCell) map[i][j]).getCollectible() instanceof Vaccine)
					remainingVaccines++;
			}
		}
		for (Hero hero : heroes) {
			remainingVaccines += hero.getVaccineInventory().size();
		}
		return heroes.size() >= 5 && remainingVaccines == 0;
	}

	public static boolean checkGameOver() {
		if (heroes.size() > 0) {
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if (map[i][j] instanceof CollectibleCell
							&& ((CollectibleCell) map[i][j]).getCollectible() instanceof Vaccine)
						return false;
				}
			}
			for (Hero hero : heroes) {
				if (hero.getVaccineInventory().size() > 0)
					return false;
			}
		}
		return true;
	}

	public static void startGame(Hero h) {
		heroes.add(h);
		availableHeroes.remove(h);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new CharacterCell(null);
			}
		}

		((CharacterCell) map[0][0]).setCharacter(h);
		h.setLocation(new Point(0, 0));

		spawnCollectibles();
		for (int i = 0; i < 10; i++) {
			spawnNewZombie();
		}
		spawnTraps();
		adjustVisibility(h);
	}

	public static void spawnCollectibles() {
		for (int i = 0; i < 5; i++) {
			Vaccine v = new Vaccine();
			int x, y;
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while ((map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() != null)
					|| (map[x][y] instanceof CollectibleCell) || (map[x][y] instanceof TrapCell));
			map[x][y] = new CollectibleCell(v);

			vaccinesPoint[i] = new Point(x,y);
		}
		for (int i = 0; i < 5; i++) {
			Supply v = new Supply();
			int x, y;
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while ((map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() != null)
					|| (map[x][y] instanceof CollectibleCell) || (map[x][y] instanceof TrapCell));
			map[x][y] = new CollectibleCell(v);
		}
	}

	public static void spawnTraps() {
		for (int i = 0; i < 5; i++) {
			int x, y;
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while ((map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() != null)
					|| (map[x][y] instanceof CollectibleCell) || (map[x][y] instanceof TrapCell));
			map[x][y] = new TrapCell();
		}
	}
































}
