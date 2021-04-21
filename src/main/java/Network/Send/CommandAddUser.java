/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Send;

import Logic.Game;
import UI.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tato třída reprezentuje co se stane když se odešle příkaz ADDUSER. Žádá o
 * registraci usera.
 *
 * @author Miloslav Fico
 */
public class CommandAddUser implements ISendCommands {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String NAME_OF_COMMAND = StringCommandsSend.ADDUSER.toString().toUpperCase();
    private final Game game;
    private final UI ui;

    public CommandAddUser(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
    }

    @Override
    public String doCommand() {

        logger.debug("Snaha o registraci klienta:" + ui.getTempName());
        return NAME_OF_COMMAND + "/" + ui.getTempName() + "/" + ui.getTempPasswd() + ";";

    }

    @Override
    public String getName() {
        return NAME_OF_COMMAND;
    }
}
