package atm.command;

import atm.User;
import atm.account.*;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.EmptyStackException;


@AllArgsConstructor
public class CommandExecutor {

    private User loggedUser;
    private AccountRepository accountRepository;

    public void fromUserInput(String input) throws UnknownCommandException, NoSuchAccountException {
        String[] words = input.split(" ");
        if (input.startsWith("withdraw") && words.length == 2 && words[1].matches("\\d+")) {
            BigDecimal amount = new BigDecimal(words[1]);
            withdrawCommandExecutor(amount);
        } else if (input.startsWith("deposit") && words.length == 2 && words[1].matches("\\d+")) {
            BigDecimal amount = new BigDecimal(words[1]);
            depositCommandExecutor(amount);
        } else if (input.startsWith("transfer") && words.length == 3 && words[1].matches("\\d+")) {
            BigDecimal amount = new BigDecimal(words[1]);
            String toAccount = words[2];

            transferCommandExecutor(amount, toAccount);
        } else if (input.equals("undo")) {
            try {
                System.out.println("Operation " + CommandHistorySaving.commandHistorySaving.peek().getCommand().description() + " was cancelled");
                CommandHistorySaving.commandHistorySaving.peek().getCommand().undo();
            } catch (EmptyStackException e) {
                System.out.println("No action done yet!");
            }
        } else if (input.equals("history")) {
            printHistory();
        } else {
            throw new UnknownCommandException("Invalid input");
        }
    }

    private void transferCommandExecutor(BigDecimal amount, String toAccountNumber) throws NoSuchAccountException {
        Account account = accountRepository.getByNumber(loggedUser.getAccountNumber());
        BigDecimal balance = account.getBalance();

        if (balance.compareTo(amount) <= 0) {
           System.out.println("Not enough balance!");
        }else if(!accountRepository.findByNumber(toAccountNumber)){
            System.out.println("Wrong receivers account details. Account does not exist.");
        }else {
            Command transferCommand = new TransferCommand(loggedUser.getAccountNumber(), toAccountNumber, accountRepository, amount);
            transferCommand.execute();
            System.out.println(transferCommand.description());
        }
    }

    private void depositCommandExecutor(BigDecimal amount) throws NoSuchAccountException {

        Command depositCommand = new DepositCommand(amount, accountRepository, loggedUser.getAccountNumber());
        depositCommand.execute();
        System.out.println(depositCommand.description());

    }

    private void withdrawCommandExecutor(BigDecimal amount) throws NoSuchAccountException {
        Account account = accountRepository.getByNumber(loggedUser.getAccountNumber());

        if (account.getBalance().compareTo(amount) <= 0) {
            System.out.println("Not enough funds");
        }
        else {

            Command withdrawCommand = new WithdrawCommand(amount, accountRepository, loggedUser.getAccountNumber());
            withdrawCommand.execute();
            System.out.println(withdrawCommand.description());
        }

    }


    public void printCurrentBalance(Account updatedAccount) {
        System.out.println("Current account balance : £" + accountRepository.getByNumber(updatedAccount.getAccountNumber()).getBalance());
    }

    void printHistory() {
        if(CommandHistorySaving.commandHistorySaving.isEmpty()){
            System.out.println("No action done yet!");
        }else {
            CommandHistorySaving.commandHistorySaving.forEach(c -> System.out.println(c.getLocalDateTime() + " -> " + c.getCommand().description()));
        }

    }


}
