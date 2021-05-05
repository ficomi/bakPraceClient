/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Send;

import Logic.Game;
import Security.Cipher;
import UI.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

/**
 * Tato třída reprezentuje co se stane když se odešle příkaz STARTCOM. Snaho o
 * login na server.
 *
 * @author Miloslav Fico
 */
public class CommandSendPubKey implements ISendCommands {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String NAME_OF_COMMAND = StringCommandsSend.PUBKEY.toString().toUpperCase();
    private final Game game;
    private final UI ui;

    public CommandSendPubKey(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
    }

    @Override
    public String doCommand() {
        return "PUBKEY/"+ Base64.getEncoder().encodeToString(Cipher.getPublicKey().getEncoded());
    }

    @Override
    public String getName() {
        return NAME_OF_COMMAND;
    }

}
