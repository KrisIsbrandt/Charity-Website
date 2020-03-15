package pl.coderslab.charity.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private Type type;
    private Boolean expired;
    private Date created;
    private Date expiryDate;

    private Date calculateExpirayDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public VerificationToken(User user, String token, Type type) {
        super();
        this.user = user;
        this.token = token;
        this.type = type;
        this.expiryDate = calculateExpirayDate(EXPIRATION);
        this.expired = false;
    }

    public VerificationToken() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    @PrePersist
    public void setCreated() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        this.created = new Date(calendar.getTime().getTime());
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int expiryTimeInMinutes) {
        this.expiryDate = calculateExpirayDate(expiryTimeInMinutes);
    }

    public enum Type {
        ACCOUNT_ACTIVATION,
        PASSWORD_RESET;
    }
}
