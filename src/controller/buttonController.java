/*
 * Author: Yang Hong
 * Class: buttonController.java
 * Description: This class extends JPanel. It is a button controlling panel
 * 				of the game GUI. It contains two JPanels that each contains
 * 				four direction buttons and an instruction button. It is the
 * 				only entry to change the status of the game.
 */

package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Game;

public class buttonController extends JPanel{

	private JPanel moveHolder;
	private JPanel shootHolder;
	private JButton moveHunter;
	private JButton shootArrow;
	private JButton moveUp;
	private JButton moveDown;
	private JButton moveLeft;
	private JButton moveRight;
	private JButton shootUp;
	private JButton shootDown;
	private JButton shootLeft;
	private JButton shootRight;
	private Game game;
	
	public buttonController(Game game){
		this.game = game;
		this.moveHolder = new JPanel();
		this.shootHolder = new JPanel();
		
		this.moveHunter = new JButton("Move");
		this.moveUp = new JButton("^");
		this.moveDown = new JButton("v");
		this.moveLeft = new JButton("<");
		this.moveRight = new JButton(">");
		
		this.shootArrow = new JButton("Shoot");
		this.shootUp = new JButton("^");
		this.shootDown = new JButton("v");
		this.shootLeft = new JButton("<");
		this.shootRight = new JButton(">");
		
		setupButtonLayout();
		
		this.setSize(250, 500);
		this.setLocation(0, 0);
		this.setLayout(new GridLayout(2, 0));
		this.add(moveHolder);
		this.add(shootHolder);
	}
	
	private void setupButtonLayout() {
		// ----------move buttons----------
		moveHunter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Choose a direction to MOVE.\n(Beware of your surroundings!)");
			}
		});

		moveUp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.changeGameState("moveUp");
			}
		});

		moveDown.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.changeGameState("moveDown");
			}
		});

		moveLeft.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.changeGameState("moveLeft");
			}
		});

		moveRight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.changeGameState("moveRight");
			}
		});
		
		moveHolder.setPreferredSize(new Dimension(250, 250));
		moveHolder.setLayout(new GridLayout(3, 3));
		moveHolder.setBackground(Color.BLACK);
		moveHolder.add(new emptyButton());
		moveHolder.add(moveUp);
		moveHolder.add(new emptyButton());
		moveHolder.add(moveLeft);
		moveHolder.add(moveHunter);
		moveHolder.add(moveRight);
		moveHolder.add(new emptyButton());
		moveHolder.add(moveDown);
		moveHolder.add(new emptyButton());
		
		// ----------shoot buttons----------
		shootArrow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Choose a direction to SHOOT.\n(you only have ONE arrow, so make it count!)");
			}
		});
		
		shootUp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.changeGameState("shootUp");
			}
		});

		shootDown.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.changeGameState("shootDown");
			}
		});

		shootLeft.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.changeGameState("shootLeft");
			}
		});

		shootRight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.changeGameState("shootRight");
			}
		});
		
		shootHolder.setPreferredSize(new Dimension(250, 250));
		shootHolder.setLayout(new GridLayout(3, 3));
		shootHolder.setBackground(Color.BLACK);
		shootHolder.add(new emptyButton());
		shootHolder.add(shootUp);
		shootHolder.add(new emptyButton());
		shootHolder.add(shootLeft);
		shootHolder.add(shootArrow);
		shootHolder.add(shootRight);
		shootHolder.add(new emptyButton());
		shootHolder.add(shootDown);
		shootHolder.add(new emptyButton());
	}
	
	private class emptyButton extends JButton{	
		public emptyButton(){
			this.setText("");
			this.setEnabled(false);
		}
	}
	
	public void resetButtons(boolean state){
		moveHunter.setEnabled(state);
		moveUp.setEnabled(state);
		moveDown.setEnabled(state);
		moveLeft.setEnabled(state);
		moveRight.setEnabled(state);
		
		shootArrow.setEnabled(state);
		shootUp.setEnabled(state);
		shootDown.setEnabled(state);
		shootLeft.setEnabled(state);
		shootRight.setEnabled(state);
	}
	
}
