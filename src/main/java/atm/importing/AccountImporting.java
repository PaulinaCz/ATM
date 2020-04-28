package atm.importing;

import atm.account.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Getter
public class AccountImporting {

    private final String accountsPath;


    public List<Account> getAccounts() throws IOException {

        return Files.readAllLines(Paths.get(accountsPath)).stream()
                .map(line -> line.split(","))
                .map(this::toAccount)
                .collect(Collectors.toList());

    }

    private Account toAccount(String[] strings) {
        String accountNumber = strings[0];
        String balance = balanceWithoutCurrency(strings[1]);

        return new Account(accountNumber, new BigDecimal(balance));
    }

    private String balanceWithoutCurrency(String balance){

        return balance.replace("£", "");

    }
}
