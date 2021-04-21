/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Recive;

import Logic.Game;
import UI.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Tato třída reprezentuje co se stane když příjde příkaz LOG.
 * Vrací jestli se user přihlásil k serveru.
 *
 * @author Miloslav Fico
 */
public class CommandLogin implements IReciveCommands {
    private final String NAME = StringCommandsRecive.LOG.toString().toUpperCase();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Game game;
    private final UI ui;
    private final Recive recive;

    public CommandLogin(Game game, UI ui, Recive recive) {
        this.game = game;
        this.ui = ui;
        this.recive = recive;
    }


    @Override
    public void doCommand(String[] values) {


        if (values[1].equals("0")) {
            logger.debug("Server odmítnul login.");
            ui.failedLogin();
        } else {
            game.setLocalPlayer(values[1], Integer.valueOf(values[3]), values[2]);
            ui.startGameMenu();
            logger.debug("Server příjmul login pro: " + values[1]);
        }


    }

    @Override
    public String getName() {
        return NAME;
    }
}
