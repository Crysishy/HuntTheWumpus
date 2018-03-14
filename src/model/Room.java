/*
 * Author: Yang Hong
 * Class: Room.java
 * Description: This class is a crucial component of the program. It constructs
 * 				the Room object and hold all information within the room: hunter,
 * 				wumpus, pit, blood, slime, goop and visited. It is able to add
 * 				any element into the room and check element existence within
 * 				each room. It is able to return a String of any element as info
 * 				in the room and is able to return images representing each element
 * 				which will be used in GraphicView class.
 */

package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Room {

	private boolean hunter;
	private boolean wumpus;
	private boolean blood;
	private boolean slime;
	private boolean goop;
	private boolean pit;
	private boolean visited;

	// constructor
	public Room() {
		hunter = false;
		wumpus = false;
		blood = false;
		slime = false;
		goop = false;
		pit = false;
		visited = false;
	}
	
	// for test purposes
	public void resetRoom(){
		hunter = false;
		wumpus = false;
		blood = false;
		slime = false;
		goop = false;
		pit = false;
		visited = false;
	}
	
	private void cleanTheGround(){
		blood = false;
		slime = false;
		goop = false;
	}

	// ----------add elements----------
	public void addHunter() {
		hunter = true;
	}
	
	public void deleteHunter() {
		hunter = false;
	}

	public void addWumpus() {
		wumpus = true;
	}

	public void addBlood() {
		if(wumpus || pit){
			this.cleanTheGround();
			return;
		}
		if (slime) {
			goop = true;
			blood = false;
			slime = false;
			return;
		}
		blood = true;
	}

	public void addSlime() {
		if(wumpus || pit){
			this.cleanTheGround();
			return;
		}
		if (blood) {
			goop = true;
			blood = false;
			slime = false;
			return;
		}
		slime = true;
	}

	public void addPit() {
		pit = true;
	}

	public void addVisited() {
		visited = true;
	}

	// ----------check elements----------
	public boolean hasHunter() {
		return hunter;
	}

	public boolean hasWumpus() {
		return wumpus;
	}

	public boolean hasBlood() {
		return blood;
	}

	public boolean hasSlime() {
		return slime;
	}

	public boolean hasPit() {
		return pit;
	}

	public boolean hasGoop() {
		return goop;
	}

	public boolean hasVisited() {
		return visited;
	}

	public String toString() {
		if (visited) {
			if (hunter)
				return "O";
			else if (wumpus)
				return "W";
			else if (goop)
				return "G";
			else if (blood)
				return "B";
			else if (slime)
				return "S";
			else if (pit)
				return "P";
			else
				return " ";
		} else
			return "X";
	}

	public Image getGroundImage() {
		return importImage("WumpusImageSet/Ground.png");
	}
	
	public Image getElementImage() {
		if (wumpus)
			return importImage("WumpusImageSet/Wumpus.png");
		if (pit)
			return importImage("WumpusImageSet/SlimePit.png");
		if (goop)
			return importImage("WumpusImageSet/Goop.png");
		if (blood)
			return importImage("WumpusImageSet/Blood.png");
		if (slime)
			return importImage("WumpusImageSet/Slime.png");
			
		return importImage("WumpusImageSet/Ground.png");
	}
	
	public Image getHunterImage() {
		return importImage("WumpusImageSet/TheHunter.png");
	}

	private Image importImage(String imageLocation) {
		try {
			return ImageIO.read(new File(imageLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
