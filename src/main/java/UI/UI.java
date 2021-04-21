/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Logic.Game;
import Network.Network;
import UI.observers.*;

/**
 * Tato třída se stará o propojeni "síťové časti" s graficou částí.
 *
 * @author Miloslav Fico
 */
public final class UI {
    private String tempName = "";
    private String tempPasswd = "";
    private String chatMsg = "";
    private boolean playerTurn;
    private boolean won;

    private final Game game;
    private final Network network;

    private String name = "";
    private char[] passwd;

    /**
     * Javafx observers
     */
    private RegisterObserver registerObserver;
    private LoginObserver loginObserver;
    private ChatObserver chatObserver;
    private StartGameObserver startGameObserver;
    private GameObserver gameObserver;

    /**
     * UI constructor
     *
     * @param game
     * @param network
     */
    public UI(Game game, Network network) {
        this.game = game;
        this.network = network;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized char[] getPasswd() {
        return passwd;
    }

    public synchronized String getTempName() {
        return tempName;
    }

    public synchronized void setTempName(String text) {
        this.tempName = text;
    }

    public synchronized String getTempPasswd() {
        return tempPasswd;
    }

    public synchronized void setTempPasswd(String text) {
        this.tempPasswd = text;
    }

    /**
     * Notifies registerObserver about successful registration
     */
    public synchronized void succsefullReg() {
        registerObserver.successfulRegistration();
    }

    /**
     * Notifies loginObserver about failed login
     */
    public synchronized void failedLogin() {
        loginObserver.failedLogin();
    }

    /**
     * Notifies registerObserver about failed registration
     */
    public synchronized void failedRegistration() {
        registerObserver.failedRegistration();
    }

    /**
     * Notifies loginObserver about successful login.
     */
    public synchronized void startGameMenu() {
        loginObserver.successfulLogin();
    }

    /**
     * Notifies gameObserver about game end.
     */
    public synchronized void endGameMenu() {
        gameObserver.closeGameStartMenu();
    }

    public synchronized void setMessage(String text) {
        chatMsg = text;
    }

    /**
     * Notifies chat observer about chat update.
     *
     * @param text
     */
    public synchronized void recChat(String text) {
        chatObserver.updateChat(text);
    }

    public synchronized String getChatMsg() {
        return chatMsg;
    }

    /**
     * Notifies startGameObserver about game start.
     *
     * @param bool
     */
    public synchronized void startGame(boolean bool) {
        setPlayerTurn(bool);
        startGameObserver.startGame();
    }

    public synchronized void getActualField() {
        gameObserver.setButtonsValue();
    }

    public synchronized void setCardsBackside() {
        gameObserver.setAllCardsToBackside();
    }

    public synchronized void setButtons() {
        gameObserver.setAvailableCards();
    }

    public synchronized boolean isPlayerTurn() {
        return playerTurn;
    }

    public synchronized void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public synchronized void setScoresOnGamePanel() {
        gameObserver.setScores();
    }

    /**
     * Returns registerObserver for updating javafx elements.
     *
     * @return RegisterObserver
     */
    public RegisterObserver getRegisterObserver() {
        return registerObserver;
    }

    /**
     * Sets registerObserver to UI
     *
     * @param registerObserver
     */
    public void setRegisterObserver(RegisterObserver registerObserver) {
        this.registerObserver = registerObserver;
    }

    /**
     * Returns loginObserver for updating javafx elements.
     *
     * @return LoginObserver
     */
    public LoginObserver getLoginObserver() {
        return loginObserver;
    }

    /**
     * Sets loginObserver to UI
     *
     * @param loginObserver
     */
    public void setLoginObserver(LoginObserver loginObserver) {
        this.loginObserver = loginObserver;
    }

    /**
     * Sets chatObserver to UI
     *
     * @param chatObserver
     */
    public void setChatObserver(ChatObserver chatObserver) {
        this.chatObserver = chatObserver;
    }

    /**
     * Returns chatObserver for updating javafx elements.
     *
     * @return ChatObserver
     */
    public ChatObserver getChatObserver() {
        return chatObserver;
    }

    /**
     * Sets startGameObserver to UI
     *
     * @param startGameObserver
     */
    public void setStartGameObserver(StartGameObserver startGameObserver) {
        this.startGameObserver = startGameObserver;
    }

    /**
     * Returns startGameObserver for updating javafx elements.
     *
     * @return startGameObserver
     */
    public StartGameObserver getStartGameObserver() {
        return startGameObserver;
    }

    /**
     * Sets gameObserver to UI
     *
     * @param gameObserver
     */
    public void setGameObserver(GameObserver gameObserver) {
        this.gameObserver = gameObserver;
    }

    /**
     * Returns gameObserver for updating javafx elements.
     *
     * @return gameObserver
     */
    public GameObserver getGameObserver() {
        return gameObserver;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }

    public Boolean getWon() {
        return won;
    }
}
