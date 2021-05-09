/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Recive;

import Logic.Game;
import Network.NetworkThreadName;
import Network.Send.CommandMapSend;
import Network.Send.StringCommandsSend;
import Security.Cipher;
import UI.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

/**
 * @author pix
 */
public class CommandSetCommunication implements IReciveCommands {

    private final String NAME = StringCommandsRecive.SETCOMMUNICATION.toString().toUpperCase();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Game game;
    private final UI ui;
    private final Recive recive;

    public CommandSetCommunication(Game game, UI ui, Recive recive) {
        this.game = game;
        this.ui = ui;
        this.recive = recive;
    }

    @Override
    public void doCommand(String[] values) {

        try {

            Cipher.createKeys(values[1],values[3],Integer.parseInt(values[2]),recive.getNetwork());

            CommandMapSend.setRequiredCommand(StringCommandsSend.PUBKEY);
            recive.getNetwork().getNetworkThread(NetworkThreadName.SENDER).interrupt();
            logger.debug("Přijat veřejný klíč serveru");

        } catch (Exception e) {
            logger.error("Chyba :" + e);
        }

    }

    @Override
    public String getName() {
        return NAME;
    }
}
