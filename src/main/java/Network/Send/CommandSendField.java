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
 * Tato třída reprezentuje co se stane když se odešle příkaz FIELD. Odesílá
 * svoje upravené pole s kartami protihráči.
 *
 * @author Miloslav Fico
 */
public class CommandSendField implements ISendCommands {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String NAME_OF_COMMAND = StringCommandsSend.FIELD.toString().toUpperCase();
    private final Game game;
    private final UI ui;

    public CommandSendField(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
    }

    @Override
    public String doCommand() {

        logger.debug("Odesláno herní pole");
        return NAME_OF_COMMAND + "/" + game.getField().getFieldToText() + "/" + game.getLocalPlayer().getScore() + "/" + !ui.isPlayerTurn() + "/" + game.getSecondPlayer().getName() + ";";

    }

    @Override
    public String getName() {
        return NAME_OF_COMMAND;
    }
}
