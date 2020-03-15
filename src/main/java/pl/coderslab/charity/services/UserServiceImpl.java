package pl.coderslab.charity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.dto.UserDto;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.entities.VerificationToken.Type;
import pl.coderslab.charity.repositories.UserRepository;
import pl.coderslab.charity.repositories.VerificationTokenRepository;

import java.util.List;
import java.util.UUID;

import static pl.coderslab.charity.entities.User.Role.ROLE_USER;
import static pl.coderslab.charity.entities.VerificationToken.getEXPIRATION;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User hashPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public User registerNewUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(ROLE_USER);
        user.setActive(false);
        user.setCreated();

        return userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token, Type type) {
        VerificationToken verificationToken = new VerificationToken(user, token, type);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        String newToken = UUID.randomUUID().toString();
        verificationToken.setToken(newToken);
        verificationToken.setExpiryDate(getEXPIRATION());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken findTokenByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    @Override
    public List<VerificationToken> findAllTokensByUserAndType(User user, Type type) {
        return verificationTokenRepository.findAllByUserAndType(user, type);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token).getUser();
    }

    @Override
    public void expireVerificationToken(VerificationToken token) {
        token.setExpired(true);
        verificationTokenRepository.save(token);
    }
}
