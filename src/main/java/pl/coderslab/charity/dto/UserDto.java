package pl.coderslab.charity.dto;

import javax.validation.constraints.NotEmpty;

public class UserDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
    private String password2;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    public UserDto(@NotEmpty String email, @NotEmpty String password, @NotEmpty String password2, @NotEmpty String firstName, @NotEmpty String lastName) {
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean samePassord() {
        return this.password.equals(this.password2);
    }
}
