package atm.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Stack;

@AllArgsConstructor
@Getter
public class CommandHistorySaving {

    private CommandHistory commandHistory;
    public static Stack<CommandHistory> commandHistorySaving = new Stack<>();


}
