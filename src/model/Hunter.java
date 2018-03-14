/*
 * Author: Yang Hong
 * Class: Hunter.java
 * Description: This class is a crucial component of the program. It holds position
 * 				of the Object - Hunter. It is able to change the object's position
 * 				when any four of the moving methods is called upon it.
 */

package model;

import java.awt.Point;

public class Hunter {

	private Point position;
	
	public Hunter(Point initial){
		position = initial;
	}
	
	public Point getPosition(){
		return position;
	}
	
	public void moveUp(){
		position.x = (position.x - 1 + Map.numOfRows_verti) % Map.numOfRows_verti;
	}
	
	public void moveDown(){
		position.x = (position.x + 1) % Map.numOfRows_verti;
	}
	
	public void moveLeft(){
		position.y = (position.y - 1 + Map.numOfCols_horiz) % Map.numOfCols_horiz;
	}
	
	public void moveRight(){
		position.y = (position.y + 1) % Map.numOfCols_horiz;
	}
}
