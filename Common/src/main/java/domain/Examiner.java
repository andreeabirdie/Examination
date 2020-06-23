package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Examiners", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class Examiner implements Serializable {

    private String username;
    private String password;

    public Examiner() {
    }

    public Examiner(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Id
    @Column(name="username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
