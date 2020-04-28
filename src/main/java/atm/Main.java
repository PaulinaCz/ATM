package atm;

import atm.account.Account;
import atm.account.AccountRepository;

import atm.account.IllegalAmountException;
import atm.account.NoSuchAccountException;
import atm.client.ATM;
import atm.command.UnknownCommandException;
import atm.importing.AccountImporting;
import atm.importing.UserImporting;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, UnknownCommandException, NoSuchAccountException, IllegalAmountException {

        System.out.println("Welcome to ABC bank");

        List<User> userList = new UserImporting("C:\\SDA\\ATM\\src\\main\\resources\\users.csv").getUsers();
        List<Account> accountList = new AccountImporting("C:\\SDA\\ATM\\src\\main\\resources\\accounts.csv").getAccounts();


        new ATM(userList, AccountRepository.of(accountList)).run();

    }
}
