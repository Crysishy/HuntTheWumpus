/*
 * Author: Yang Hong
 * Class: HuntTheWumpusGUI.java
 * Description: This class extends JFrame. It is the main interface of the game.
 * 				It contains button controlling panel and game status view panels.
 * 				It offers options to start a new game or terminate the program;
 * 				it also offers options to change views while playing the game.
 */

package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import model.Game;
import model.Map;
import view.GraphicView;
import view.TextAreaView;

public class HuntTheWumpusGUI extends JFrame{

	private JPanel gameViewPanel;
	private GraphicView graphicView;
	private TextAreaView textAreaView;
	private Game game;
	//private Map gameMap;
	
	public static void main(String[] args) {
		HuntTheWumpusGUI gameGUI = new HuntTheWumpusGUI();
		gameGUI.setVisible(true);
	}

	public HuntTheWumpusGUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(755, 550);
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);
	    this.setTitle("Hunt The Wumpus");
	    
	    initializeGame();
	    setupMenus();
	    setUpPanels();
	}

	private void setupMenus() {
		JMenuItem option = new JMenu("Options");
		JMenuItem newGame = new JMenuItem("New Game");
		option.add(newGame);
		JMenuItem exitGame = new JMenuItem("Quit");
		option.add(exitGame);
		
		JMenuItem view = new JMenu("Views");
		JMenuItem textView = new JMenuItem("Text Area View");
		view.add(textView);
		JMenuItem graphicView = new JMenuItem("Graphic View");
		view.add(graphicView);
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		menuBar.add(option);
		menuBar.add(view);
		
		MenuItemListener menuListener = new MenuItemListener();
		newGame.addActionListener(menuListener);
		exitGame.addActionListener(menuListener);
		textView.addActionListener(menuListener);
		graphicView.addActionListener(menuListener);
	}
	
	private void setUpPanels(){
		graphicView = new GraphicView(game);
	    game.addObserver(graphicView);
	    textAreaView = new TextAreaView(game);
		game.addObserver(textAreaView);

		setViewTo(graphicView);
	}
	
	private void initializeGame() {
		game = new Game();
		//gameMap = game.getGameMap();
	    
	}
	
	private class MenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = ((JMenuItem) e.getSource()).getText();
			
			if (text.equals("New Game"))
				game.startNewGame();
			
			if (text.equals("Quit"))
				System.exit(0);
			
			if (text.equals("Text Area View"))
				setViewTo(textAreaView);
			
			if (text.equals("Graphic View"))
				setViewTo(graphicView);
		}
	}
	
	private void setViewTo(JPanel newView) {
		if (gameViewPanel != null)
			remove(gameViewPanel);
		gameViewPanel = newView;
		add(gameViewPanel);
		gameViewPanel.repaint();
		validate();
	}
}
