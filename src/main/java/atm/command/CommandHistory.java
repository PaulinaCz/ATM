package atm.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CommandHistory {
    private Command command;
    private LocalDateTime localDateTime;
}
