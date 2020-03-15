package pl.coderslab.charity.services;

import pl.coderslab.charity.dto.UserDto;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.entities.VerificationToken.Type;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    User hashPassword(User user);

    User registerNewUser(UserDto userDto);

    void createVerificationToken(User user, String token, Type tye);

    VerificationToken generateNewVerificationToken(String token);

    VerificationToken getVerificationToken(String token);

    VerificationToken findTokenByUser(User user);

    List<VerificationToken> findAllTokensByUserAndType(User user, VerificationToken.Type type);

    User findByEmail(String email);

    User findByVerificationToken(String token);

    void expireVerificationToken(VerificationToken token);
}
