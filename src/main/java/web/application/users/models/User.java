package web.application.users.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.IncrementGenerator;
import web.application.users.config.annotations.PhoneNumber;
import web.application.users.config.annotations.Username;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "auto-increment")
    @GenericGenerator(name = "auto-increment", type = IncrementGenerator.class)
    private long id;

    @Username
    private String username;

    @NotEmpty(message = "It should not be empty!")
    @Email(message = "Invalid E-mail. Use format: ******@domen.xxx")
    private String email;

    @PhoneNumber
    @Column(name = "phone_number")
    private String phoneNumber;

    public User() {}

    public User(String username, String email, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("User {ID: %d, Username: %s, Phone: %s, E-mail: %s}", id, username, phoneNumber, email);
    }
}
