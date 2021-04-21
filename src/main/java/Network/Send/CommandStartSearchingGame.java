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
 * Tato třída reprezentuje co se stane když se odešle příkaz SSEARCH. Snaha o
 * požádání serveru o přidání do fronty hráčů kteří hledají hru.
 *
 * @author Miloslav Fico
 */
public class CommandStartSearchingGame implements ISendCommands {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String NAME_OF_COMMAND = StringCommandsSend.SSEARCH.toString().toUpperCase();
    private final Game game;
    private final UI ui;

    public CommandStartSearchingGame(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
    }

    @Override
    public String doCommand() {
        String name = game.getLocalPlayer().getName();
        String passwd = game.getLocalPlayer().getPassword();

        if ((name != null || !name.isEmpty()) && (passwd != null || !passwd.isEmpty())) {

            if (!game.isIsSearching()) {
                System.out.println(name + " t " + passwd);
                game.setIsSearching(true);
                return NAME_OF_COMMAND + "/" + name + "/" + passwd + "/true/" + ";";

            } else {
                System.out.println(name + " f " + passwd);
                game.setIsSearching(false);
                return NAME_OF_COMMAND + "/" + name + "/" + passwd + "/false/" + ";";

            }

        }


        return "";
    }

    @Override
    public String getName() {
        return NAME_OF_COMMAND;
    }

}
