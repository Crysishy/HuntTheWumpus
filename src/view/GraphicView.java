/*
 * Author: Yang Hong
 * Class: GraphicView.java
 * Description: This class extends JPanel and implements Observer. It is a graphic view
 * 				option for the game. It has two main components: button controlling panel
 * 				and image view window. It receive messages regarding the game whenever
 * 				the game state has changed and it immediately update the graphic window.
 */

package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controller.buttonController;
import model.Game;

public class GraphicView extends JPanel implements Observer{

	private Game game;
	private buttonController buttonPanel;
	private ImagePanel imagePanel;
	
	public GraphicView(Game currentGame) {
		this.game = currentGame;
		this.setPreferredSize(null);
		buttonPanel = new buttonController(game);
		this.add(buttonPanel);
		imagePanel = new ImagePanel(game);
		this.add(imagePanel);
		imagePanel.repaint();
		setUpLayout();
	}

	private void setUpLayout() {
		this.setLayout(null);
		
	}
	
	private void updateImagePanel(){
		this.remove(imagePanel);
		imagePanel = new ImagePanel(game);
		this.add(imagePanel);
		imagePanel.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		updateImagePanel();
		buttonPanel.resetButtons(true);
		if (game.gameOver())
			buttonPanel.resetButtons(false);
	}

}
