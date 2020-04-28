package atm.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class Account {
    private final String accountNumber;
    private final BigDecimal balance;


    public Account deposit(BigDecimal amount){
        return new Account(accountNumber, balance.add(amount));
    }


    public Account withdraw(BigDecimal amount){
        return new Account(accountNumber, balance.subtract(amount));
    }

}
