package Logic;

import static org.junit.Assert.*;
/**
 * Test který se vztahuje k třídě Card
 */
public class CardTest {

    /**
     * Oveření hodnoty karty
     */
    @org.junit.Test
    public void getValue() {
        Card card = new Card(10,false,true);
        assertEquals(10,card.getValue());
    }

    /**
     * Ověření otočení karty
     */
    @org.junit.Test
    public void setFlip() {
        Card card = new Card(10,false,true);
        card.setFlip(true);
        assertTrue(card.isFlipped());
    }

    /**
     * Ověření "vyřešenosti karty"
     */
    @org.junit.Test
    public void setClickable() {
        Card card = new Card(10,false,true);
        card.setClickable(false);
        assertFalse(card.isClickable());
    }
}