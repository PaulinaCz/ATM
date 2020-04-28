package atm.command;

import atm.account.Account;
import atm.account.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DepositCommand implements Command {

    private BigDecimal amount;
    private AccountRepository accountRepository;
    private String accountNumber;


    @Override
    public void execute() {

        Account account = accountRepository.getByNumber(accountNumber);
        CommandHistorySaving.commandHistorySaving.push(new CommandHistory(new DepositCommand(amount, accountRepository, accountNumber), LocalDateTime.now()));
        accountRepository.save(account.deposit(amount));

    }

    @Override
    public void undo(){

        Account account = accountRepository.getByNumber(accountNumber);
        CommandHistorySaving.commandHistorySaving.pop();
        accountRepository.save(account.withdraw(amount));


    }

    @Override
    public String description() {
        return "Deposit of £" + amount + " was made to account #" + accountNumber;
    }

}
