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
public class CommandDecryptAsymKey implements IReciveCommands {

    private final String NAME = StringCommandsRecive.SYMKEY.toString().toUpperCase();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Game game;
    private final UI ui;
    private final Recive recive;

    public CommandDecryptAsymKey(Game game, UI ui, Recive recive) {
        this.game = game;
        this.ui = ui;
        this.recive = recive;
    }

    @Override
    public void doCommand(String[] values) {

        try {
            String key = "";
            for (int i = 1; i < values.length; i++) {
                key += "/" + values[i];
            }
            key = key.substring(1,key.length());
            Cipher.decryptAsymKey(Base64.getDecoder().decode(key));;

            System.out.println("Přijat asym klíč: " + key);

            CommandMapSend.setRequiredCommand(StringCommandsSend.STARTENCRYPT);
            recive.getNetwork().getNetworkThread(NetworkThreadName.SENDER).interrupt();

        } catch (Exception e) {
            logger.error("Chyba :" + e);
        }

    }

    @Override
    public String getName() {
        return NAME;
    }
}
