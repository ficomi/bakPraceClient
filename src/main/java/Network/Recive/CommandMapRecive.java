/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Recive;

import java.util.HashMap;

/**
 * V této třídě se uchovávají všechny příkazy.
 *
 * @author Miloslav Fico
 */

public class CommandMapRecive {

    private final HashMap<String, IReciveCommands> RECIVE_COMMANDS;

    public CommandMapRecive() {
        RECIVE_COMMANDS = new HashMap<>();

    }

    public void AddCommandToMap(IReciveCommands command) {
        RECIVE_COMMANDS.put(command.getName(), command);
    }

    public boolean isCommand(String key) {
        return RECIVE_COMMANDS.containsKey(key);
    }

    public IReciveCommands getCommandClass(String key) {
        return RECIVE_COMMANDS.get(key);
    }

}
