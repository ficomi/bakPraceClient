package Logic;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test který se vztahuje na třídu Field
 */
public class FieldTest {

    /**
     * Test na ověření kartového pole
     */
    @Test
    public void getNumberOfCards() {
        Field cardField = new Field(6,true,null);
        assertEquals(6,cardField.getNumberOfCards());
    }

    /**
     * Test na získání karty z pole a vložení pole karet
     */
    @Test
    public void getCard() {
        Field cardField = new Field(6,true,null);
        cardField.setFieldFromText("0|false|true|2|false|true|1|false|true|1|false|true|2|false|true|0|false|true|");
        assertEquals(0,cardField.getCard(0).getValue());
    }

    /**
     * Test na vložení pole karet pomocí textu
     */
    @Test
    public void getFieldToText() {
        Field cardField = new Field(6,true,null);
        String cards = "0|false|true|2|false|true|1|false|true|1|false|true|2|false|true|0|false|true|";
        cardField.setFieldFromText(cards);
        assertEquals(cards,cardField.getFieldToText());
    }

    /**
     * Test porovnávající dvě karty
     */
    @Test
    public void compareTwoCards() {
        Field cardField = new Field(6,true,null);
        cardField.setFieldFromText("0|false|true|2|false|true|1|false|true|1|false|true|2|false|true|0|false|true|");
        assertTrue(cardField.compareTwoCards(cardField.getCard(0),cardField.getCard(cardField.getField().size()-1)));
    }

}