/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import Logic.Game;
import Network.Recive.Recive;
import Network.Send.Send;
import Security.Cipher;
import UI.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * V této tříde se spoští thready pro příjem a odeslaní.
 *
 * @author Miloslav Fico
 */
public class Network {

    Game game;
    ConcurrentHashMap<NetworkThreadName, Thread> threadList;
    UI ui;
    private final Logger logger = LoggerFactory.getLogger(Network.class);
    private boolean isConnected;

    public Network(Game game) {
        threadList = new ConcurrentHashMap<>();
        this.game = game;
        try {

            Cipher.createKeys("RSA", "AES", 2048,this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startNetwork() {

        threadList.put(NetworkThreadName.SENDER, new Thread(new Send(NetworkThreadName.SENDER, game, ui,this)));
        threadList.put(NetworkThreadName.RECIVER, new Thread(new Recive(NetworkThreadName.RECIVER, game, ui,this)));

        isConnected = true;

        for (NetworkThreadName name : threadList.keySet()) {
            threadList.get(name).start();
            logger.debug("Startuji network Thread: " + name);
        }

    }

    public synchronized Thread getNetworkThread(NetworkThreadName name) {
        return threadList.get(name);

    }

    public void setUI(UI ui) {
        this.ui = ui;
        startNetwork();


    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnect(boolean bol){
        isConnected=bol;
    }
}
