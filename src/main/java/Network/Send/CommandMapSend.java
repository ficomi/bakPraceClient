/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Send;

/**
 * V této tříde jsou uloženy všechny příkazy které jsou spojené s odesláním příkazů na server.
 *
 * @author Miloslav Fico
 */

import java.util.HashMap;

public class CommandMapSend {
    private final HashMap<String, ISendCommands> SENT_COMMANDS;
    private static StringCommandsSend requiredCommand;

    public CommandMapSend() {
        SENT_COMMANDS = new HashMap<>();

    }

    public void AddCommandToMap(ISendCommands command) {
        SENT_COMMANDS.put(command.getName(), command);
    }

    public void doCommand(String key) {
        SENT_COMMANDS.get(key).doCommand();
    }

    public boolean isCommand(String key) {
        return SENT_COMMANDS.containsKey(key);
    }

    public ISendCommands getCommandClass(String key) {
        return SENT_COMMANDS.get(key);
    }

    public static synchronized void setRequiredCommand(StringCommandsSend command) {
        requiredCommand = command;
    }

    public static synchronized String getRequiredCommand() {
        return requiredCommand.toString().toUpperCase();
    }
}
