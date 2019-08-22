package controller;

import controller.command.ICommand;

public class NullCommand implements ICommand {
    @Override
    public void execute() {
        System.out.println("No command selected");
    }

    @Override
    public String getCommandName() {
        return "Null Command";
    }
}
