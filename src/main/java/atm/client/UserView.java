package atm.client;

import atm.User;
import atm.account.Account;
import atm.account.AccountRepository;
import atm.command.CommandExecutor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import atm.account.NoSuchAccountException;
import atm.command.UnknownCommandException;
import java.util.Scanner;

@RequiredArgsConstructor
@Getter
public class UserView {

    private final User loggedUser;
    private final AccountRepository accountRepository;


    public void run() throws NoSuchAccountException {
        CommandExecutor commandExecutor = new CommandExecutor(loggedUser, accountRepository);

        Scanner in = new Scanner(System.in);

        while(true) {
            String menuOption = in.nextLine();
            if (menuOption.equals("help")) {
                printHelp();
            } else if (menuOption.equals("exit")) {
                System.out.println("BYE!");
                System.exit(0);
            } else if (menuOption.equals("logout")) {
                System.out.println("You are logged out");
            } else if (menuOption.equals("balance")) {
               printBalance();
            } else {
                try {
                    commandExecutor.fromUserInput(menuOption);
                } catch(UnknownCommandException e){
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    private void printBalance() {

        CommandExecutor commandExecutor = new CommandExecutor(loggedUser, accountRepository);
        Account account = accountRepository.getByNumber(loggedUser.getAccountNumber());
        commandExecutor.printCurrentBalance(account);


    }

    private void printHelp(){
        System.out.println("Available actions:\n" +
                "type 'help' - to print menu\n" +
                "type 'exit' - to exit the program\n" +
                "type 'logout' - to log out of current session\n" +
                "type 'history' - to print current session's history\n" +
                "type 'undo' - to cancel last operation\n" +
                "'withdraw <amount>'\n" +
                "'deposit <amount>'\n" +
                "type 'balance' - to check current account balance\n" +
                "'transfer <amount> <target account #>' - transfer money from your account to other");
    }
}
