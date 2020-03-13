package pl.coderslab.charity.services;

import pl.coderslab.charity.dto.UserDto;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.entities.VerificationToken;

public interface UserService {

    void saveUser(User user);

    User hashPassword(User user);

    User registerNewUser(UserDto userDto);

    void createVerificationToken(User user, String token);

    VerificationToken generateNewVerificationToken(String token);

    VerificationToken getVerificationToken(String token);

    VerificationToken findTokenByUser(User user);

    User findByEmail(String email);

    User findByVerificationToken(String token);
}
