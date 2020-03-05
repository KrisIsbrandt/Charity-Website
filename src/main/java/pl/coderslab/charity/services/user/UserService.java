package pl.coderslab.charity.services.user;

import pl.coderslab.charity.dto.UserDto;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.entities.VerificationToken;

public interface UserService {

    void saveUser(User user);

    User registerNewUser(UserDto userDto);

    void createVerificationToken(User user, String Token);

    VerificationToken getVerificationToken(String token);

    User findByEmail(String email);

    User findByVerificationToken(String token);
}
