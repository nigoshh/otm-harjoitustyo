package sudokuinsika.domain;

/**
 * Represents a user.
 */
public class User {

    private int id;
    private String username;
    private byte[] pwHash;
    private byte[] pwSalt;
    private int pwIterations;
    private int pwKeyLength;
    private String email;

    /**
     * Sole constructor.
     *
     * @param username the user's username
     */
    public User(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPwHash(byte[] pwHash) {
        this.pwHash = pwHash;
    }

    public byte[] getPwHash() {
        return pwHash;
    }

    public void setPwSalt(byte[] pwSalt) {
        this.pwSalt = pwSalt;
    }

    public byte[] getPwSalt() {
        return pwSalt;
    }

    public void setPwIterations(int pwIterations) {
        this.pwIterations = pwIterations;
    }

    public int getPwIterations() {
        return pwIterations;
    }

    public void setPwKeyLength(int pwKeyLength) {
        this.pwKeyLength = pwKeyLength;
    }

    public int getPwKeyLength() {
        return pwKeyLength;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
