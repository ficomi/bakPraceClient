/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Tato třída reprezentuje pole karet.
 *
 * @author Miloslav Fico
 */
public class Field {

    final private int NUMBER_OF_CARDS;
    private Game game;
    final private ArrayList<Card> field;
    boolean host;
    int move;

    public Field(int cards, boolean isHost, Game game) {
        if (cards % 2 != 0) {
            cards++;
        }
        NUMBER_OF_CARDS = cards;
        this.game = game;
        host = isHost;
        move = 0;
        field = new ArrayList<>();
        if (isHost) {
            setField();
        }

    }

    private void setField() {

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < NUMBER_OF_CARDS / 2; j++) {
                field.add(new Card(j, false, true));
            }
        }
        Collections.shuffle(field);
    }

    public int getNumberOfCards() {
        return NUMBER_OF_CARDS;
    }

    public ArrayList getField() {
        return field;
    }

    public Card getCard(int position) {
        return field.get(position);
    }

    public synchronized String getFieldToText() {
        String text = "";
        for (Card card : field) {
            text += card.getValue() + "|" + card.isFlipped() + "|" + card.isClickable() + "|";
        }

        return text;
    }

    public synchronized void setFieldFromText(String text) {
        field.clear();
        String[] splitText = text.split("\\|");
        int step = 1;
        for (int i = 0; i < splitText.length; i++) {

            if (step % 3 == 0) {
                field.add(new Card(Integer.parseInt(splitText[i - 2]), Boolean.valueOf(splitText[i - 1]), Boolean.valueOf(splitText[i])));
            }
            step++;
        }
    }

    public synchronized boolean compareTwoCards(Card firstCard, Card secondCard) {
        if (firstCard.getValue() == secondCard.getValue()) {
            firstCard.setClickable(false);
            secondCard.setClickable(false);
            if (game != null) {
                game.getLocalPlayer().addScore();
            }


            return true;
        }

        return false;

    }


    public synchronized boolean isHost() {
        return host;
    }


    public synchronized void nextMove() {
        move++;

    }

    public synchronized void moveReset() {

        move = 0;
    }

    public synchronized int getMove() {
        return move;
    }

}
