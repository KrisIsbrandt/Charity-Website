package pl.coderslab.charity.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoggedUser extends User {
    private final pl.coderslab.charity.entities.User user;

    public LoggedUser(String email,
                      String password,
                      Collection<? extends GrantedAuthority> authorities,
                      pl.coderslab.charity.entities.User user) {
        super(email, password, authorities);
        this.user = user;
    }
    public pl.coderslab.charity.entities.User getUser() {
        return user;
    }

}
