/*
 * Author: Yang Hong
 * Class: Map.java
 * Description: This class is a crucial component of the program. It constructs
 * 				the overall map of the game. It contains all the info of each room
 * 				which means it has direct access to Room class. It is a core part
 * 				of the class Game and each public method can be called by Game.
 * 				It is able to generate a randomly set game map; return info of
 * 				each room as current game state; set the whole map visible and
 * 				return a String of the current map as visual text view.
 */

package model;

import java.awt.Point;
import java.util.Random;

public class Map {

	public final static int numOfRows_verti = 10;
	public final static int numOfCols_horiz = 10;
	private Point hunter;
	private Point wumpus;
	private Point[] pit;
	private Room[][] map;
	
	public Map(){
		map = new Room[numOfRows_verti][numOfCols_horiz];
		Random random = new Random();
		// randomly pick 3-5 pits
		pit = new Point[random.nextInt(3) + 3];
		// randomly generate positions of hunter and wumpus
		hunter = new Point(random.nextInt(numOfRows_verti), random.nextInt(numOfCols_horiz));
		wumpus = new Point(random.nextInt(numOfRows_verti), random.nextInt(numOfCols_horiz));
		// if distance between hunter and wumpus < 3
		// re-generate wumpus's position
		while (distanceCheck(hunter, wumpus, 3))
			wumpus = new Point(random.nextInt(numOfRows_verti), random.nextInt(numOfCols_horiz));
		// randomly generate pits' positions
		for(int i = 0; i < pit.length; i++)
			pit[i] = new Point(random.nextInt(numOfRows_verti), random.nextInt(numOfCols_horiz));
		// check distance among pits and hunter/wumpus
		int currentIndex = 0;
		for (Point currentPit : pit){
			
			boolean pitsDuplicated = checkPitsDistance(currentPit, currentIndex);
			
			while (distanceCheck(currentPit, hunter, 2) || distanceCheck(currentPit, wumpus, 1) || pitsDuplicated){
				currentPit = new Point(random.nextInt(numOfRows_verti), random.nextInt(numOfCols_horiz));
				pit[currentIndex] = currentPit;
				pitsDuplicated = checkPitsDistance(currentPit, currentIndex);
			}
			currentIndex++;
		}
		// set up the map
		for(int row = 0; row < numOfRows_verti; row++){
			for(int col = 0; col < numOfCols_horiz; col++){
				map[row][col] = new Room();
				Point temp = new Point(row, col);
				// hunter's location
				if (hunter.equals(temp)){
					map[row][col].addHunter();
					map[row][col].addVisited();
				}
				// wumpus's location
				if (wumpus.equals(temp))
					map[row][col].addWumpus();
				// blood's location
				if (distanceCheck(temp, wumpus, 2))
					map[row][col].addBlood();
				// pits and slime's location
				for(Point currentPit : pit){
					if (currentPit.equals(temp))
						map[row][col].addPit();
					if (distanceCheck(temp, currentPit, 1))
						map[row][col].addSlime();
				}
			}
		}
	}

	private boolean checkPitsDistance(Point targetPit, int index) {
		for (int i = 0; i < index; i++){
			if (distanceCheck(targetPit, pit[i], 1))
				return true;
		}
		return false;
	}

	private boolean distanceCheck(Point myPosition, Point target, int distance) {
		// 2 points are in line
		if (myPosition.distance(target) <= distance)
			return true;
		// check row distance
		Point temp = new Point(myPosition.x, myPosition.y);
		if (numOfRows_verti - myPosition.x <= distance)
			temp.x -= numOfRows_verti;
		else if(myPosition.x <= distance)
			temp.x += numOfRows_verti;
		if(temp.distance(target) <= distance)
			return true;
		// check col distance
		temp.x = myPosition.x;
		if (numOfCols_horiz - myPosition.y <= distance)
			temp.y -= numOfCols_horiz;
		else if(myPosition.y <= distance)
			temp.y += numOfCols_horiz;
		if(temp.distance(target) <= distance)
			return true;
		
		return false;
	}
	
	// for test purposes
	public void setTestMap(){
		for(int row = 0; row < 10; row++){
			for(int col = 0; col < 10; col++){
				map[row][col].resetRoom();
			}
		}
		hunter = new Point(0, 1);
		map[0][1].addHunter();
		wumpus = new Point(0, 2);
		map[0][2].addWumpus();
		pit = new Point[1];
		pit[0] = new Point(0, 0);
		map[0][0].addPit();
		map[1][0].addBlood();
		map[1][1].addSlime();
		map[1][2].addBlood();
		map[1][2].addSlime();
	}
	
	public void updateHunter(Point position){
		hunter = position;
		for(int row = 0; row < numOfRows_verti; row++){
			for(int col = 0; col < numOfCols_horiz; col++){
				if (row == position.x && col == position.y){
					map[row][col].addHunter();
					map[row][col].addVisited();
				}
				else
					map[row][col].deleteHunter();
			}
		}
	}
	
	public boolean shotWumpus(String direction, Point hunterPosition){
		if (direction.equals("North") || direction.equals("South")){
			if(hunterPosition.y == wumpus.y)
				return true;
		}
		if (direction.equals("West") || direction.equals("East")){
			if(hunterPosition.x == wumpus.x)
				return true;
		}
		return false;
	}
	
	public Point getHunterPosition(){
		return hunter;
	}
	
	public Room getRoom(Point position){
		return map[position.x][position.y];
	}
	
	public String roomInfo() {
		if (map[hunter.x][hunter.y].hasWumpus())
			return "\n I see the WUMPUS...(Game Over)";
		if (map[hunter.x][hunter.y].hasPit())
			return "\n I see a PIT...(Game Over)";
		if (map[hunter.x][hunter.y].hasGoop())
			return "\n I see GOOP on the floor.";
		if (map[hunter.x][hunter.y].hasBlood())
			return "\n I see BLOOD on the floor.";
		if (map[hunter.x][hunter.y].hasSlime())
			return "\n I see SLIME on the floor.";
		
		return "\n I see NOTHING in this room.";
	}
	
	public void setGameMapVisible(){
		for(int row = 0; row < numOfRows_verti; row++){
			for(int col = 0; col < numOfCols_horiz; col++){
				map[row][col].addVisited();
			}
		}
	}
	
	public String toString() {
		String result = "\n";
		for (int row = 0; row < numOfRows_verti; row++) {
			result += "  ";
			for (int col = 0; col < numOfCols_horiz; col++) {
				result += " [" + map[row][col].toString() + "]";
			}
			result += "\n\n";
		}
		return result;
	}
}