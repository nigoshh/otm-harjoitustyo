package sudokuinsika.domain;

import java.util.Objects;

public class User {
    private String username;
    private String pwHash;
    private String email;

    public User(String username, String pwHash, String email) {
        this.username = username;
        this.pwHash = pwHash;
        this.email = email;
    }

    public User(String username) {
        this.username = username;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public String getEmail() {
        return email;
    }
}
