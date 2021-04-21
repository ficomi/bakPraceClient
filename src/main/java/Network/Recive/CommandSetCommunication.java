/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Recive;

import Logic.Game;
import Security.Communication;
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

            String key = "";
            for (int i = 1; i < values.length; i++) {
                key+="/"+values[i];
            }

            key = key.substring(1,key.length());
            logger.debug("Přijak encryptovaný AES klíč: "+key);

            Communication.decryptAsymKey(Base64.getDecoder().decode(key));

        } catch (Exception e) {
            logger.error("Chyba :" + e);
        }

    }

    @Override
    public String getName() {
        return NAME;
    }
}
