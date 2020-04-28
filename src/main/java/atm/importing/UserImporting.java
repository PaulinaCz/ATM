package atm.importing;

import atm.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class UserImporting {
    private final String userPath;

    public List<User> getUsers() throws IOException {

        return Files.readAllLines(Paths.get(userPath))
                .stream()
                .map(line -> line.split(","))
                .map(this::toUser)
                .collect(Collectors.toList());

    }

    public User toUser(String[] strings){

        String login = strings[0];
        String password = strings[1];
        String accountNumber = strings[2];
        return new User(login, password, accountNumber);

    }
}
