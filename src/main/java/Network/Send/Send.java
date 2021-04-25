/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Send;

/**
 * Třída která se stará o odesílaní příkazu na server.
 *
 * @author Miloslav Fico
 */

import Logic.Game;
import Network.Network;
import Network.NetworkThreadName;
import Security.Cipher;
import UI.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

public class Send implements Runnable {


    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final NetworkThreadName NAME;
    private PrintWriter writer;
    private final String ADRESS = "localhost";
    private final int PORT = 5010;
    private Socket socket;
    private CommandMapSend mapOfCommands;
    private final Game game;
    private final UI UI;
    private final Network network;

    public Send(NetworkThreadName name, Game game, UI UI, Network network) {
        this.NAME = name;
        this.game = game;
        this.UI = UI;
        this.network = network;


        //nastaven jako prvni command "navaz spojeni se serverem"
        try {

            socket = new Socket(ADRESS, PORT);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.println("STARTCOM/" + Base64.getEncoder().encodeToString(Cipher.getPublicKey().getEncoded()) + ";");
            writer.flush();
            //prvni komuinikace se serverem
        } catch (IOException e) {
            logger.error("Nepodařilo se připojit k serveru od SENDER: " + e);
            closeConnectionSend();

        }
    }


    @Override
    public void run() {

        //vytvoreni mapy prikazu
        mapOfCommands = new CommandMapSend();
        //plneni mapy prikazu
        mapOfCommands.AddCommandToMap(new CommandSendField(game, UI));
        mapOfCommands.AddCommandToMap(new CommandSendMsg(game, UI));
        mapOfCommands.AddCommandToMap(new CommandAddUser(game, UI));
        mapOfCommands.AddCommandToMap(new CommandStartSearchingGame(game, UI));
        mapOfCommands.AddCommandToMap(new CommandEndGame(game, UI));
        mapOfCommands.AddCommandToMap(new CommandLogin(game, UI));
        mapOfCommands.AddCommandToMap(new CommandStartEncryption(game, UI));

        try {


            while (network.isConnected()) {

                Thread.currentThread().sleep(100);

            }
            closeConnectionSend();

        } catch (InterruptedException e) {
            try {
                String message = "";
                if (Cipher.isEncrypted) {
                    message = Cipher.encrypt(mapOfCommands.getCommandClass(CommandMapSend.getRequiredCommand()).doCommand());
                } else {
                    message = mapOfCommands.getCommandClass(CommandMapSend.getRequiredCommand()).doCommand();
                }
                logger.debug("Odeslána zpráva: " + message);
                writer.println(message);  // Tomáš je čůrák
                writer.flush();
            } catch (Exception i) {
                logger.error("Nepovedlo se odeslat zprávu ", i);
            }

            if (network.isConnected()) {
                run();
            }

        }


    }


    public NetworkThreadName getNAME() {
        return NAME;
    }

    private void closeConnectionSend() {
        try {
            socket.close();
            writer.close();
        } catch (IOException e) {
            logger.error("Nepodařilo se ukončit spojení k serveru od SENDER: " + e);

        }

    }

}
