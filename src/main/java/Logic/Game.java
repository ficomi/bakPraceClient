/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tato třída reprezentuje vše co je spojené s hrou (hráče,pole a doplňkové věci).
 *
 * @author Miloslav Fico
 */
public class Game implements Observable {
    private Field field;
    private final int NUMBER_OF_CARDS;
    private final Logger logger = LoggerFactory.getLogger(Game.class);
    private boolean isSearching = false;
    private Player playerlocal;
    private Player player2;

    public Game(int cards) {
        NUMBER_OF_CARDS = cards;

    }

    public synchronized void startGame(boolean isHost) {

        isSearching = false;
        field = new Field(NUMBER_OF_CARDS, isHost, this);
        logger.debug("Hra se spustila.");
    }

    public synchronized void setSecondPlayer(String name, int elo) {
        player2 = new Player(name);
        player2.setElo(elo);
        logger.debug("Pridán druhý hráč.");
    }

    public synchronized void setLocalPlayer(String name, int Elo, String password) {
        playerlocal = new Player(name, password);
        playerlocal.setElo(Elo);
        logger.debug("Pridán lokální hráč.");
    }


    public synchronized Player getLocalPlayer() {
        return playerlocal;
    }

    public synchronized Player getSecondPlayer() {
        return player2;
    }

    public synchronized Field getField() {
        return field;
    }

    public boolean isIsSearching() {
        return isSearching;
    }

    public void setIsSearching(boolean isSearching) {
        this.isSearching = isSearching;
    }


    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }
}
