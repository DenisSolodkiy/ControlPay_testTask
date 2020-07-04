package ui;

import PageObjects.*;
import TestCommons.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UploadUserPhotoTest {

    List<UserTestData> userTestData = Arrays.asList(
            new UserTestData(1, "John", "Willington", 24, "admin@test.com", "qwerty1", "Admin",
                    Arrays.asList(UserPermissions.READ, UserPermissions.CREATE, UserPermissions.DELETE, UserPermissions.BULK_CREATE, UserPermissions.UPDATE)),
            new UserTestData(2, "Peter", "Stones", 29, "Junior accountant", "qwerty2",
                    "Junior accountant", Arrays.asList(UserPermissions.READ)),
            new UserTestData(3, "Mark", "Ditriht", 42, "accountant@test.com", "qwerty3", "Accountant",
                    Arrays.asList(UserPermissions.READ, UserPermissions.CREATE)),
            new UserTestData(4, "Maxime", "Trudeau", 31, "s.accountant@test.com", "qwerty4", "Senior accountant",
                    Arrays.asList(UserPermissions.READ, UserPermissions.CREATE, UserPermissions.UPDATE)),
            new UserTestData(5, "Derek", "Wolfer", 20, "manager@test.com", "qwerty5", "Manager",
                    Arrays.asList(UserPermissions.READ, UserPermissions.CREATE, UserPermissions.BULK_CREATE, UserPermissions.UPDATE)));

    @ParameterizedTest(name = "{index} - user ID: {0}")
    @ValueSource(ints = {0,1,2,3,4})
    public void uploadUserPhotoPositiveTest(int userId) {
        WebDriver webDriver = TestsUtils.initializeDriver();

        webDriver.get("https://login-page.com");
        LoginPage loginPage = new LoginPage(webDriver);

        IntermediatePage intermediatePage = loginPage.performLogin(userTestData.get(userId).login, userTestData.get(userId).password);
        ProfilePage profilePage = intermediatePage.goToTheProfile();

        assertEquals("no image uploaded", profilePage.getPhotoFileName());

        profilePage.uploadProfileImage("/home/data/photo_for_test_user.jpg");

        assertEquals("photo_for_test_user.jpg", profilePage.getPhotoFileName());
    }
}
