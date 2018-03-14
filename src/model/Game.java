/*
 * Author: Yang Hong
 * Class: Game.java
 * Description: This class extends Observable. It is the core part of the program.
 * 				It is able to start a new game; change the game state; check the
 * 				game state; store game info and return these info to game GUI.
 * 				It has access to the game component classes: Map, Room and Hunter.
 * 				It sends synchronized info to its observers whenever state is changed.
 */

package model;

import java.awt.Point;
import java.util.Observable;

public class Game extends Observable{

	private boolean death_Pit;
	private boolean death_Wumpus;
	private boolean death_Arrow;
	private boolean gameOver;
	private boolean gameWon;
	private Hunter hunter;
	private Map map;
	
	public Game(){
		initializeVariables();
	}
	
	public void startNewGame(){
		initializeVariables();
		setChanged();
		notifyObservers();
	}
	
	// for test purposes
	public void setTestGame(){
		map.setTestMap();
		hunter = new Hunter(map.getHunterPosition());
	}

	private void initializeVariables() {
		death_Pit = false;
		death_Wumpus = false;
		death_Arrow = false;
		gameOver = false;
		gameWon = false;
		map = new Map();
		hunter = new Hunter(map.getHunterPosition());
	}
	
	public void changeGameState(String command){
		switch(command){
		case "moveUp":
			hunter.moveUp();
			map.updateHunter(hunter.getPosition());
			break;
		case "moveDown":
			hunter.moveDown();
			map.updateHunter(hunter.getPosition());
			break;
		case "moveLeft":
			hunter.moveLeft();
			map.updateHunter(hunter.getPosition());
			break;
		case "moveRight":
			hunter.moveRight();
			map.updateHunter(hunter.getPosition());
			break;
		case "shootUp":
			gameOver = true;
			gameWon = map.shotWumpus("North", hunter.getPosition());
			break;
		case "shootDown":
			gameOver = true;
			gameWon = map.shotWumpus("South", hunter.getPosition());
			break;
		case "shootLeft":
			gameOver = true;
			gameWon = map.shotWumpus("West", hunter.getPosition());
			break;
		case "shootRight":
			gameOver = true;
			gameWon = map.shotWumpus("East", hunter.getPosition());
			break;
		default:
			System.out.println("Error: Invalid command.");
		}
		checkGameState();
		setChanged();
		notifyObservers(this);
	}

	private void checkGameState() {
		for(int row = 0; row < Map.numOfRows_verti; row++){
			for(int col = 0; col < Map.numOfCols_horiz; col++){
				
				Room currentRoom = map.getRoom(new Point(row, col));
				
				if (currentRoom.hasWumpus() && currentRoom.hasHunter()){
					death_Wumpus = true;
					gameOver = true;
				}
				if (currentRoom.hasPit() && currentRoom.hasHunter()){
					death_Pit = true;
					gameOver = true;
				}
			}
		}
		if (gameOver && !gameWon && !death_Wumpus && !death_Pit)
			death_Arrow = true;
	}
	
	public boolean gameOver(){
		return gameOver;
	}
	
	public boolean gameWon(){
		return gameWon;
	}
	
	public boolean deathByWumpus(){
		return death_Wumpus;
	}
	
	public boolean deathByPit(){
		return death_Pit;
	}
	
	public boolean deathByArrow(){
		return death_Arrow;
	}
	
	public Map getGameMap(){
		return map;
	}
}
