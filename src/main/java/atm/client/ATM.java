package atm.client;

import atm.User;
import atm.account.AccountRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import atm.account.NoSuchAccountException;
import atm.command.UnknownCommandException;
import atm.command.WrongEntryException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@RequiredArgsConstructor
@Getter
public class ATM {

    private final List<User> userList;
    private final AccountRepository accountRepository;


    public void run() throws UnknownCommandException, NoSuchAccountException {
        UserView userView = login();
        userView.run();
    }


    private UserView login() throws UnknownCommandException{

        Scanner in = new Scanner(System.in);

        while(true) {
        System.out.println("Type: 'login <user> <password>' to log in");
        System.out.println("Type: 'exit' to terminate>");

        String input = in.nextLine();

            if (input.startsWith("login")) {
                try {
                    User loggedInUser = getUser(input.split(" "));
                    System.out.println("Login successful");
                    return new UserView(loggedInUser, accountRepository);

                } catch (WrongEntryException e) {
                    System.out.println(e.getMessage());
                }
            }else if (input.startsWith("exit")) {
                System.out.println("BYE!");
                System.exit(0);
            } else {
                System.out.println("You need to login first");
            }

        }

    }

    private User getUser(String[] strings) throws UnknownCommandException, WrongEntryException {
        if(strings.length < 3){
            throw new UnknownCommandException("Invalid data");
        }

        return getUser(strings[1], strings[2]).orElseThrow(WrongEntryException::new);
    }

    private Optional<User> getUser(String login, String encryptedPassword) {
        return userList.stream()
                .filter(u -> u.getLogin().equals(login))
                .filter(u -> u.getPassword().equals(encryptedPassword))
                .findAny();
    }

}
