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
 * Tato třída reprezentuje co se stane když se odešle příkaz ENDGAME.
 *
 * @author Miloslav Fico
 */
public class CommandEndGame implements ISendCommands {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String NAME_OF_COMMAND = StringCommandsSend.EGAME.toString().toUpperCase();
    private final Game game;
    private final UI ui;

    public CommandEndGame(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
    }


    @Override
    public String doCommand() {
        logger.debug("Odesílání konce hry serveru.");
        game.getLocalPlayer().setScore(0);
        return NAME_OF_COMMAND + "/" + game.getLocalPlayer().getName() + "/" + game.getSecondPlayer().getName() + ";";
    }


    @Override
    public String getName() {
        return NAME_OF_COMMAND;
    }
}