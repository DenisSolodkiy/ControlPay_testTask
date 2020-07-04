package TestCommons;

import java.util.List;

public class UserTestData {
    public int id;
    public String expectedFirstName;
    public String expectedLastName;
    public int expectedAge;
    public String login;
    public String password;
    public String expectedRole;
    public List<UserPermissions> expectedUserPermissions;

    public UserTestData(int id,
                        String expectedFirstName,
                        String expectedLastName,
                        int expectedAge,
                        String login,
                        String password,
                        String expectedRole,
                        List<UserPermissions> expectedUserPermissions) {
        this.id = id;
        this.expectedFirstName = expectedFirstName;
        this.expectedLastName = expectedLastName;
        this.expectedAge = expectedAge;
        this.login = login;
        this.password = password;
        this.expectedRole = expectedRole;
        this.expectedUserPermissions = expectedUserPermissions;
    }
}