package Logic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Skupina testů pro třídu Game.
 */
public class GameTest {

    /**
     * Test na spoštění hry
     */
    @Test
    public void startGame() {
        Game game = new Game(5);
        game.startGame(true);
        assertTrue(game.getField().isHost());
        assertEquals(6,game.getField().getField().size());
    }

    /**
     * Test na vytvoření druhého hráče
     */
    @Test
    public void setSecondPlayer() {
        Game game = new Game(5);
        game.setSecondPlayer("pepa",100);
        assertEquals("pepa",game.getSecondPlayer().getName());
        assertEquals(100, game.getSecondPlayer().getElo());
    }

    /**
     * Test na vytvoření lokálního hráče
     */
    @Test
    public void setLocalPlayer() {
        Game game = new Game(6);
        game.setLocalPlayer("pepa",100, "pepa");
        assertEquals("pepa",game.getLocalPlayer().getName());
        assertEquals(100,game.getLocalPlayer().getElo());
        assertEquals("pepa",game.getLocalPlayer().getPassword());
    }

    /**
     * Test na vytvořenou velikost pole karet
     */
    @Test
    public void getField() {
        Game game = new Game(6);
        game.startGame(true);
        assertEquals(6,game.getField().getField().size());
    }

    /**
     * Test na změnu stavu vyhledávání
     */
    @Test
    public void setIsSearching() {
        Game game = new Game(6);
        game.setIsSearching(true);
        assertTrue(game.isIsSearching());
    }
}