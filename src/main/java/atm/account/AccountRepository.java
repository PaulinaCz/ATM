package atm.account;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AccountRepository {

    private final Map<String, Account> accountMap = new HashMap<>();

    public static AccountRepository of(List<Account> accountList) {
        AccountRepository accountRepository = new AccountRepository();
        accountList.forEach(accountRepository::save);
        return accountRepository;
    }


    public Account getByNumber(String accountNumber){

        try {
            if (!accountMap.containsKey(accountNumber)) {
                throw new NoSuchAccountException("The account doesn't exist");
            }
        }
        catch (NoSuchAccountException e){
            e.getLocalizedMessage();
        }

        return accountMap.get(accountNumber);

    }

    public void save(Account account) {
        accountMap.put(account.getAccountNumber(), account);
    }

    public boolean findByNumber(String toAccount) {
        return accountMap.containsKey(toAccount);
    }
}
