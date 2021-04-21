/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Recive;

/**
 * Tato třída se stará o zpracování všech příchozích zprav a spuštění příkazu který odpovídá příchozí zprávě.
 *
 * @author Miloslav Fico
 */

import Logic.Game;
import Network.Network;
import Network.NetworkThreadName;
import Security.Communication;
import UI.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Recive implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(Recive.class);
    private final NetworkThreadName NAME;
    private BufferedReader reader;
    private final String ADRESS = "localhost";
    private final int PORT = 5010;
    private Socket socket;
    private CommandMapRecive mapOfCommands;
    private final Game game;
    private final UI UI;
    private final Network network;


    public Recive(NetworkThreadName name, Game game, UI UI, Network network) {
        this.NAME = name;
        this.game = game;
        this.UI = UI;
        this.network = network;
    }


    public void start() {
        mapOfCommands = new CommandMapRecive();
        mapOfCommands.AddCommandToMap(new CommandRecMsg(game, UI, this));
        mapOfCommands.AddCommandToMap(new CommandLogin(game, UI, this));
        mapOfCommands.AddCommandToMap(new CommandRegistration(game, UI, this));
        mapOfCommands.AddCommandToMap(new CommandStartGame(game, UI, this));
        mapOfCommands.AddCommandToMap(new CommandReciveField(game, this, UI));
        mapOfCommands.AddCommandToMap(new CommandUpdate(game, UI, this));
        mapOfCommands.AddCommandToMap(new CommandSetCommunication(game, UI, this));
        logger.debug("Všechny příkazy uloženy pro Recive.");
        try {
            socket = new Socket(ADRESS, PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Nemůžu se připojit k serveru od Recive: ");
        }

    }

    @Override
    public void run() {

        start();


        try {
            while (network.isConnected()) {
                String message = reader.readLine();
                System.out.println("PZ : " + message);
                if (Communication.isEncrypted) {
                    message = Communication.stringDecrypt(message);
                }


                System.out.println("PZ dešifrována : " + message);
                if (!message.isEmpty()) {
                    String[] split_message;

                    split_message = message.split("/");
                    if (mapOfCommands.isCommand(split_message[0])) {

                        mapOfCommands.getCommandClass(split_message[0]).doCommand(getValuesFromMessage(split_message));
                    }

                }
            }
            closeConnectionRecive();
        } catch (IOException e) {
            //Chyba pri prijmu
            logger.error("Nemůžu se pripojit k serveru od Recive: ");
            e.printStackTrace();
            closeConnectionRecive();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String[] getValuesFromMessage(String[] message) {
        message[message.length - 1] = message[message.length - 1].replace(";", ""); // oseka o ";"
        return message;
    }

    public void closeConnectionRecive() {
        try {
            socket.close();
            reader.close();
        } catch (IOException e) {
            logger.error("Nemůžu ukončit spojení: " + e);
        }

    }

    public NetworkThreadName getNAME() {
        return NAME;
    }
}
