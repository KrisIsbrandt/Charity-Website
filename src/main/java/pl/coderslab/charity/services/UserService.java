package pl.coderslab.charity.services;

import pl.coderslab.charity.entities.User;

public interface UserService {
    User findByEmail(String email);

    void saveUser(User user);
}
