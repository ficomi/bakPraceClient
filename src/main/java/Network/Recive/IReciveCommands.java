/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Recive;

/**
 * Tato třída slouži jako interface ke všem příchozím zprávám.
 *
 * @author Miloslav Fico
 */
public interface IReciveCommands {

    /**
     * @param values
     */
    public void doCommand(String[] values);

    /**
     * @return název příkazu
     */
    public String getName();
}
