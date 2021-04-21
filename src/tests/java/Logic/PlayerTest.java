package Logic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testy vztahující se na třídu Player
 */
public class PlayerTest {

    /**
     * Test na nastavení score
     */
    @Test
    public void setScore() {
        Player player = new Player("pepa","pepa");
        player.setScore(1);
        assertEquals(1,player.getScore());
    }

    /**
     * Test na přidání score
     */
    @Test
    public void addScore() {
        Player player = new Player("pepa","pepa");
        player.addScore();
        assertEquals(1,player.getScore());
    }

    /**
     * Test na nastavení jména hráče
     */
    @Test
    public void setName() {
        Player player = new Player("pepa","pepa");
        player.setName("adam");
        assertEquals("adam",player.getName());
    }

    /**
     * Test na nastavení ela
     */
    @Test
    public void setElo() {
        Player player = new Player("pepa","pepa");
        player.setElo(101);
        assertEquals(101,player.getElo());
    }

    /**
     * Test na nastavení hesla
     */
    @Test
    public void getPassword() {
        Player player = new Player("pepa","pepa");
        assertEquals("pepa",player.getPassword());
    }
}