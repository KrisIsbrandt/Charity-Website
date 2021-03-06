package pl.coderslab.charity.events;

import org.springframework.context.ApplicationEvent;
import pl.coderslab.charity.entities.User;

import java.util.Locale;

public class OnUserSelfActivation extends ApplicationEvent {

    private String appUrl;
    private Locale locale;
    private User user;


    public OnUserSelfActivation(User user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}