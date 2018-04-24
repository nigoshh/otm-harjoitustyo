package sudokuinsika.domain;

public class User {
    private int id;
    private String username;
    private byte[] pwHash;
    private byte[] pwSalt;
    private int pwIterations;
    private int pwKeyLength;
    private String email;

    public User(String username) {
        this.username = username;
    }

    public void setPwHash(byte[] pwHash) {
        this.pwHash = pwHash;
    }

    public void setPwSalt(byte[] pwSalt) {
        this.pwSalt = pwSalt;
    }

    public void setPwIterations(int pwIterations) {
        this.pwIterations = pwIterations;
    }

    public void setPwKeyLength(int pwKeyLength) {
        this.pwKeyLength = pwKeyLength;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPwHash() {
        return pwHash;
    }

    public byte[] getPwSalt() {
        return pwSalt;
    }

    public int getPwIterations() {
        return pwIterations;
    }

    public int getPwKeyLength() {
        return pwKeyLength;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}
