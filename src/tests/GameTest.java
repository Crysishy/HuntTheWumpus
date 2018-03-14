/*
 * Author: Yang Hong
 * Class: GameTest.java
 * Description: This class is a JUnit test case for all classes in package "model".
 * 				It is able to test the program under a preset game map condition.
 * 				It tests every element in the room and every room in the map.
 * 				It tests current game status when game state has changed to ensure
 * 				the game logic is correct while running.
 */

package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.Game;
import model.Map;

public class GameTest {

	@Test
	public void testMapElements() {
		Map testMap = new Map();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				testMap.getRoom(new Point(row, col)).resetRoom();
			}
		}
		
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				assertFalse(testMap.getRoom(new Point(row, col)).hasHunter());
				assertFalse(testMap.getRoom(new Point(row, col)).hasWumpus());
				assertFalse(testMap.getRoom(new Point(row, col)).hasPit());
				assertFalse(testMap.getRoom(new Point(row, col)).hasBlood());
				assertFalse(testMap.getRoom(new Point(row, col)).hasSlime());
				assertFalse(testMap.getRoom(new Point(row, col)).hasGoop());
				
				assertFalse(testMap.getRoom(new Point(row, col)).hasVisited());
				assertEquals("X", testMap.getRoom(new Point(row, col)).toString());
				
				testMap.getRoom(new Point(row, col)).addVisited();
				assertEquals(" ", testMap.getRoom(new Point(row, col)).toString());
			}
		}
		
		//  pit   hunter  wumpus
		// blood  slime    goop
		testMap.getRoom(new Point(0, 0)).addPit();
		testMap.getRoom(new Point(0, 0)).addSlime();
		assertTrue(testMap.getRoom(new Point(0, 0)).hasPit());
		assertFalse(testMap.getRoom(new Point(0, 0)).hasSlime());
		testMap.getRoom(new Point(0, 0)).getGroundImage();
		testMap.getRoom(new Point(0, 0)).getElementImage();
		
		testMap.getRoom(new Point(0, 1)).addHunter();
		assertTrue(testMap.getRoom(new Point(0, 1)).hasHunter());
		testMap.getRoom(new Point(0, 1)).getElementImage();
		testMap.getRoom(new Point(0, 1)).getHunterImage();
		
		testMap.getRoom(new Point(0, 2)).addWumpus();
		testMap.getRoom(new Point(0, 2)).addBlood();
		assertTrue(testMap.getRoom(new Point(0, 2)).hasWumpus());
		assertFalse(testMap.getRoom(new Point(0, 2)).hasBlood());
		testMap.getRoom(new Point(0, 2)).getElementImage();
		
		testMap.getRoom(new Point(1, 0)).addBlood();
		assertTrue(testMap.getRoom(new Point(1, 0)).hasBlood());
		testMap.getRoom(new Point(1, 0)).getElementImage();
		
		testMap.getRoom(new Point(1, 1)).addSlime();
		assertTrue(testMap.getRoom(new Point(1, 1)).hasSlime());
		testMap.getRoom(new Point(1, 1)).getElementImage();
		
		testMap.getRoom(new Point(1, 2)).addBlood();
		testMap.getRoom(new Point(1, 2)).addSlime();
		assertTrue(testMap.getRoom(new Point(1, 2)).hasGoop());
		assertFalse(testMap.getRoom(new Point(1, 2)).hasBlood());
		assertFalse(testMap.getRoom(new Point(1, 2)).hasSlime());
		testMap.getRoom(new Point(1, 2)).getElementImage();

		testMap.getRoom(new Point(1, 3)).addSlime();
		testMap.getRoom(new Point(1, 3)).addBlood();
		assertTrue(testMap.getRoom(new Point(1, 2)).hasGoop());
		assertFalse(testMap.getRoom(new Point(1, 2)).hasBlood());
		assertFalse(testMap.getRoom(new Point(1, 2)).hasSlime());

		assertEquals("P", testMap.getRoom(new Point(0, 0)).toString());
		assertEquals("O", testMap.getRoom(new Point(0, 1)).toString());
		assertEquals("W", testMap.getRoom(new Point(0, 2)).toString());
		assertEquals("B", testMap.getRoom(new Point(1, 0)).toString());
		assertEquals("S", testMap.getRoom(new Point(1, 1)).toString());
		assertEquals("G", testMap.getRoom(new Point(1, 2)).toString());
		
		testMap.setGameMapVisible();
		testMap.toString();	
	}
	
	@Test
	public void testDeathByPit() {
		Game game = new Game();
		game.setTestGame();
		assertTrue(!game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(!game.deathByArrow());
		assertEquals((new Point(0, 1)), game.getGameMap().getHunterPosition());
		game.changeGameState("moveUp");
		game.changeGameState("moveDown");
		game.changeGameState("moveLeft");
		assertTrue(game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(game.deathByPit());
		assertTrue(!game.deathByArrow());
	}
		
	@Test
	public void testDeathByWumpus() {
		Game game = new Game();
		game.setTestGame();
		assertTrue(!game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(!game.deathByArrow());
		assertEquals((new Point(0, 1)), game.getGameMap().getHunterPosition());
		game.changeGameState("moveRight");
		assertTrue(game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(!game.deathByArrow());
	}
		
	@Test
	public void testDeathByArrow_Up() {	
		Game game = new Game();
		game.setTestGame();
		assertTrue(!game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(!game.deathByArrow());
		assertEquals((new Point(0, 1)), game.getGameMap().getHunterPosition());
		game.changeGameState("shootUp");
		assertTrue(game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(game.deathByArrow());
	}
		
	@Test
	public void testDeathByArrow_Down() {	
		Game game = new Game();
		game.setTestGame();
		assertTrue(!game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(!game.deathByArrow());
		assertEquals((new Point(0, 1)), game.getGameMap().getHunterPosition());
		game.changeGameState("shootDown");
		assertTrue(game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(game.deathByArrow());
	}
		
	@Test
	public void testGameWon_Left() {	
		Game game = new Game();
		game.setTestGame();
		assertTrue(!game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(!game.deathByArrow());
		assertEquals((new Point(0, 1)), game.getGameMap().getHunterPosition());
		game.changeGameState("shootLeft");
		assertTrue(game.gameOver());
		assertTrue(game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(!game.deathByArrow());
	}
		
	@Test
	public void testGameWon_Right() {
		Game game = new Game();
		game.setTestGame();
		assertTrue(!game.gameOver());
		assertTrue(!game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(!game.deathByArrow());
		assertEquals((new Point(0, 1)), game.getGameMap().getHunterPosition());
		game.changeGameState("shootRight");
		assertTrue(game.gameOver());
		assertTrue(game.gameWon());
		assertTrue(!game.deathByWumpus());
		assertTrue(!game.deathByPit());
		assertTrue(!game.deathByArrow());
	}
	
	@Test
	public void testMap_RoomInfo() {
		Game game = new Game();
		game.setTestGame();
		assertEquals((new Point(0, 1)), game.getGameMap().getHunterPosition());
		assertEquals("\n I see NOTHING in this room.", game.getGameMap().roomInfo());
		
		game.changeGameState("moveLeft");
		assertEquals("\n I see a PIT...(Game Over)", game.getGameMap().roomInfo());

		game.changeGameState("moveDown");
		assertEquals("\n I see BLOOD on the floor.", game.getGameMap().roomInfo());

		game.changeGameState("moveRight");
		assertEquals("\n I see SLIME on the floor.", game.getGameMap().roomInfo());

		game.changeGameState("moveRight");
		assertEquals("\n I see GOOP on the floor.", game.getGameMap().roomInfo());

		game.changeGameState("moveUp");
		assertEquals("\n I see the WUMPUS...(Game Over)", game.getGameMap().roomInfo());
	}

}
