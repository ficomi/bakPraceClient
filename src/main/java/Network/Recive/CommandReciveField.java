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
 * Tato třída reprezentuje co se stane když příjde příkaz FIELD.
 * Refreshuje pole s kartamy.
 *
 * @author Miloslav Fico
 */
public class CommandReciveField implements IReciveCommands {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String NAME_OF_COMMAND = StringCommandsRecive.FIELD.toString().toUpperCase();
    private final Game game;
    private final Recive recive;
    private final UI ui;

    public CommandReciveField(Game game, Recive recive, UI ui) {
        this.game = game;
        this.recive = recive;
        this.ui = ui;
    }


    @Override
    public void doCommand(String[] values) {


        //logger.debug("Přijaté data o poli s kartami: "+values[1]);
        game.getField().setFieldFromText(values[1]);
        game.getSecondPlayer().setScore(Integer.parseInt(values[2]));
        ui.setScoresOnGamePanel();

        if (ui.isPlayerTurn() != Boolean.parseBoolean(values[3])) {
            ui.setCardsBackside();
        }
        logger.debug("Přijaté data o poli s kartami: " + values[3]);
        ui.setPlayerTurn(Boolean.parseBoolean(values[3]));

        ui.setButtons();
        ui.getActualField();


    }

    @Override
    public String getName() {
        return NAME_OF_COMMAND;
    }
}
