package petstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAP {

    @JsonProperty("id")
    private long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("userStatus")
    private int userStatus;

    // ── Constructors ────────────────────────────────────────────────────────────

    public UserAP() {}

    public UserAP(long id, String username, String firstName, String lastName,
                String email, String password, String phone, int userStatus) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }

    // ── Builder-style fluent setters ────────────────────────────────────────────

    public UserAP withId(long id)                   { this.id = id;             return this; }
    public UserAP withUsername(String username)      { this.username = username; return this; }
    public UserAP withFirstName(String firstName)    { this.firstName = firstName; return this; }
    public UserAP withLastName(String lastName)      { this.lastName = lastName; return this; }
    public UserAP withEmail(String email)            { this.email = email;       return this; }
    public UserAP withPassword(String password)      { this.password = password; return this; }
    public UserAP withPhone(String phone)            { this.phone = phone;       return this; }
    public UserAP withUserStatus(int userStatus)     { this.userStatus = userStatus; return this; }

    // ── Getters ─────────────────────────────────────────────────────────────────

    public long getId()           { return id; }
    public String getUsername()   { return username; }
    public String getFirstName()  { return firstName; }
    public String getLastName()   { return lastName; }
    public String getEmail()      { return email; }
    public String getPassword()   { return password; }
    public String getPhone()      { return phone; }
    public int getUserStatus()    { return userStatus; }

    // ── Setters ─────────────────────────────────────────────────────────────────

    public void setId(long id)                { this.id = id; }
    public void setUsername(String username)  { this.username = username; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public void setLastName(String lastName)  { this.lastName = lastName; }
    public void setEmail(String email)        { this.email = email; }
    public void setPassword(String password)  { this.password = password; }
    public void setPhone(String phone)        { this.phone = phone; }
    public void setUserStatus(int userStatus) { this.userStatus = userStatus; }

    // ── toString ─────────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", phone='" + phone + '\'' +
               ", userStatus=" + userStatus +
               '}';
    }

    // ── Static factory: default test user ────────────────────────────────────────

    /**
     * Returns a pre-filled User object suitable for POST/create tests.
     */
    public static UserAP defaultTestUser() {
        return new UserAP(
                100001L,
                "optimus prime",
                "Optimus",
                "Prime",
                "op@petstore.com",
                "Pass@1234",
                "9876543210",
                1
        );
    }
}
