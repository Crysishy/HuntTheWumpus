/*
 * Author: Yang Hong
 * Class: TextAreaView.java
 * Description: This class extends JPanel and implements Observer. It is a text view
 * 				option for the game. It has two main components: button controlling panel
 * 				and JTextArea view window. It receive messages regarding the game whenever
 * 				the game state has changed and it immediately update the graphic window.
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.buttonController;
import model.Game;

public class TextAreaView extends JPanel implements Observer{

	private JTextArea gameStateArea;
	private JTextArea roomInfoArea;
	private Game game;
	private buttonController buttonPanel;
	
	public TextAreaView(Game game) {
		this.game = game;
		this.setPreferredSize(null);
		buttonPanel = new buttonController(game);
		this.add(buttonPanel);
		setUpLayout();
	}

	private void setUpLayout() {
		this.setLayout(null);
		gameStateArea = new JTextArea();
		gameStateArea.setSize(500, 400);
		gameStateArea.setLocation(250, 0);
		gameStateArea.setBackground(Color.RED);
		gameStateArea.setEditable(false);
		gameStateArea.setFont(new Font("Courier", Font.BOLD, 18));
		gameStateArea.setText(game.getGameMap().toString());
		this.add(gameStateArea);
		
		roomInfoArea = new JTextArea();
		roomInfoArea.setSize(500, 100);
		roomInfoArea.setLocation(250, 400);
		roomInfoArea.setBackground(Color.BLACK);
		roomInfoArea.setEditable(false);
		roomInfoArea.setFont(new Font("Courier", Font.BOLD, 18));
		roomInfoArea.setForeground(Color.WHITE);
		roomInfoArea.setText(game.getGameMap().roomInfo());
		this.add(roomInfoArea);
	}
	
	private void updateGameStatusArea() {
		gameStateArea.setText(game.getGameMap().toString());
		roomInfoArea.setText(game.getGameMap().roomInfo());
	}

	@Override
	public void update(Observable o, Object arg) {
		updateGameStatusArea();
		buttonPanel.resetButtons(true);
		if (game.gameOver()){
			game.getGameMap().setGameMapVisible();
			updateGameStatusArea();
			buttonPanel.resetButtons(false);
			if (game.deathByWumpus())
				JOptionPane.showMessageDialog(null, "You were eaten by Wumpus!", "Game Over",
						JOptionPane.INFORMATION_MESSAGE);
			
			if (game.deathByPit())
				JOptionPane.showMessageDialog(null, "You fell to your death!", "Game Over",
						JOptionPane.INFORMATION_MESSAGE);
			
			if (game.deathByArrow())
				JOptionPane.showMessageDialog(null, "You shot yourself in the back!\n"
												  + "With your own arrow!\n"
												  + "How IRONIC!", "Game Over",
						JOptionPane.INFORMATION_MESSAGE);
			
			if(game.gameWon())
				JOptionPane.showMessageDialog(null, "You slayed the Wumpus, nicely done!", "Game Won",
						JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	

}
