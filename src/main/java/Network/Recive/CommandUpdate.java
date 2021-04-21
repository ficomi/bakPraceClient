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
 * Tato třída reprezentuje co se stane když příjde příkaz UPDATE.
 * Vrací aktualní ELO podle toho jestli hráč vyhrál nebo prohrál.
 *
 * @author Miloslav Fico
 */
public class CommandUpdate implements IReciveCommands {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String NAME = StringCommandsRecive.UPDATE.toString().toUpperCase();
    private final Game game;
    private final UI ui;
    private final Recive recive;

    public CommandUpdate(Game game, UI ui, Recive recive) {
        this.game = game;
        this.ui = ui;
        this.recive = recive;
    }


    @Override
    public void doCommand(String[] values) {
        if (game.getLocalPlayer().getElo() > Integer.valueOf(values[1])) {
            ui.setWon(false);
        } else {
            ui.setWon(true);
        }
        game.getLocalPlayer().setElo(Integer.valueOf(values[1]));
        logger.debug("Update klientských informací od serveru. Nové elo: " + values[1]);
        ui.endGameMenu();
//        ui.nextTurn();
    }

    @Override
    public String getName() {
        return NAME;
    }

}