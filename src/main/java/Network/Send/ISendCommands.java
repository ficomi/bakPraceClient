/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Send;

import Logic.Game;
import UI.UI;
import java.io.BufferedWriter;

/**
 * Tato třída slouží jako interface pro odesílací příkazy.
 * @author Miloslav Fico
 */
public interface ISendCommands {
  
    /**
     *
     *@return příkaz
     */
    
    

    public String doCommand();
    
    /**
     *
     * @return název příkazu
     */
    public String getName();
}
