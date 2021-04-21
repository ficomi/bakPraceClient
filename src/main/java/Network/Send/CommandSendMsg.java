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
 * Tato třída reprezentuje co se stane když se odešle příkaz SENDMSG.
 * Odesílá chatzující zprávu na server.
 *
 * @author Miloslav Fico
 */
public class CommandSendMsg implements ISendCommands {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String NAME_OF_COMMAND = StringCommandsSend.SENDMSG.toString().toUpperCase();
    private final Game game;
    private final UI ui;

    public CommandSendMsg(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
    }

    @Override
    public String doCommand() {

        logger.debug("Odeslána zpráva: " + ui.getChatMsg());
        return NAME_OF_COMMAND + "/" + game.getLocalPlayer().getName() + "/" + ui.getChatMsg() + ";";


    }

    @Override
    public String getName() {
        return NAME_OF_COMMAND;
    }
}
