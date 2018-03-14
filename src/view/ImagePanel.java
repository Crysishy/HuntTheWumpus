/*
 * Author: Yang Hong
 * Class: ImagePanel.java
 * Description: This class extends JPanel. It is the core component of the class GraphicView.
 * 				It overrides paintComponent(Graphic g) method therefore is able to draw
 * 				images upon the JPanel. It receives Game message from GraphicView class which
 * 				implements Observer, thus the game info is synchronized with the Game class.
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;

import model.Game;
import model.Map;
import model.Room;

public class ImagePanel extends JPanel{

	private Game game;
	private Map gameMap;
	private Point position;
	private Room room;
	
	public ImagePanel(Game currentGame){
		this.game = currentGame;
		this.gameMap = game.getGameMap();
		this.setSize(500, 500);
		this.setLocation(250, 0);
		this.setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int row_height = 0; row_height < 10; row_height++) {
			for (int col_width = 0; col_width < 10; col_width++) {
				
				position = new Point(row_height, col_width);
				room = gameMap.getRoom(position);
				
				if (room.hasVisited()) {
					g2.drawImage(room.getGroundImage(), col_width * 50, row_height * 50, this);
					g2.drawImage(room.getElementImage(), col_width * 50, row_height * 50, this);
					if (room.hasHunter())
						g2.drawImage(room.getHunterImage(), col_width * 50, row_height * 50, this);
				}
			}
		}
	}
}
