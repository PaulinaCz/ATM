package atm.command;

import atm.account.Account;
import atm.account.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TransferCommand implements Command {

    private String fromAccount;
    private String toAccount;
    private AccountRepository accountRepository;
    private BigDecimal amount;


    @Override
    public void execute(){

        transfer(fromAccount, toAccount);
        CommandHistorySaving.commandHistorySaving.push(new CommandHistory(new TransferCommand(fromAccount, toAccount, accountRepository, amount), LocalDateTime.now()));
        accountRepository.getByNumber(fromAccount);
    }

    @Override
    public void undo(){

        transfer(toAccount, fromAccount);
        CommandHistorySaving.commandHistorySaving.pop();
        accountRepository.getByNumber(fromAccount);

    }

    public void transfer(String senderAccount, String receiverAccount) {

        Account sender = accountRepository.getByNumber(senderAccount);
        Account receiver = accountRepository.getByNumber(receiverAccount);

        accountRepository.save(sender.withdraw(amount));
        accountRepository.save(receiver.deposit(amount));

    }


    @Override
    public String description() {
        return "Transferred £" + amount + " from account #" + fromAccount
                + "to account #" + toAccount;
    }
}
