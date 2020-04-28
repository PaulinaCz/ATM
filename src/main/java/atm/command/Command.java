package atm.command;

import atm.account.NoSuchAccountException;

public interface Command {

    void execute() throws NoSuchAccountException;
    void undo() throws NoSuchAccountException;
    String description();

}
