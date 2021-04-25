/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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





/**
 * Tato třída reprezentuje co se stane když příjde příkaz SSEARCH.
 *  Přidá klienta do fronty klirntů čekající na hru.
 * @author Miloslav Fico
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */





/**
 * Tato třída reprezentuje co se stane když se odešle příkaz STARTENCRYPTION
 * Odesláno potvrzení k šifrocání
 *  Přidá klienta do fronty klirntů čekající na hru.
 * @author Miloslav Fico
 */
public class CommandStartEncryption implements ISendCommands {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String NAME = StringCommandsSend.STARTENCRYPT.toString().toUpperCase();
    private final Game game;
    private final UI ui;


    public CommandStartEncryption(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
    }


    @Override
    public String doCommand()
    {
        logger.debug("Start šifrování ....");
        Cipher.isEncrypted=true;
        return"STARTENCRYPT/1;";
    }










    @Override
    public String getName() {
        return NAME;
    }
}



