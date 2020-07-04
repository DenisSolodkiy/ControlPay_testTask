package api;

import TestCommons.UserPermissions;
import TestCommons.UserTestData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

public class LoginAndGetProfileTest {

    List<UserTestData> userTestData = Arrays.asList(
            new UserTestData(1, "John", "Willington", 24, "admin@test.com", "qwerty1", "Admin",
                    Arrays.asList(UserPermissions.READ, UserPermissions.CREATE, UserPermissions.DELETE, UserPermissions.BULK_CREATE, UserPermissions.UPDATE)),
            new UserTestData(2, "Peter", "Stones", 29, "Junior accountant", "qwerty2",
                    "Junior accountant", Arrays.asList(UserPermissions.READ)),
            new UserTestData(3, "Mark", "Ditriht", 42, "accountant@test.com", "qwerty3", "Accountant",
                    Arrays.asList(UserPermissions.READ, UserPermissions.CREATE)),
            new UserTestData(4, "Maxime", "Trudeau", 31, "s.accountant@test.com", "qwerty4", "Senior accountant",
                    Arrays.asList(UserPermissions.READ, UserPermissions.CREATE,UserPermissions.UPDATE)),
            new UserTestData(5, "Derek", "Wolfer", 20, "manager@test.com", "qwerty5", "Manager",
                    Arrays.asList(UserPermissions.READ, UserPermissions.CREATE, UserPermissions.BULK_CREATE, UserPermissions.UPDATE)));


    @ParameterizedTest(name = "{index} - with user ID: {0}")
    @ValueSource(ints = {0,1,2,3,4})
    public void test(int userId) {
        RestAssured.baseURI = "https://testapp.com";
        Response tokenResponse = RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Authorization", "Base " + TestsUtils.authBase64String)
                .body(TestsUtils.prepareRequestBody(userTestData.get(userId)))
                .when()
                .post(TestsUtils.tokenEndPoint)
                .then()
                .statusCode(200).extract().response();

        TestsUtils.validateTokenEndPointResponse(tokenResponse);
        String jwt = tokenResponse.jsonPath().getString("access_token");
        List<UserPermissions> actualPermissions = TestsUtils.extractUserPermissionsFromJWT(jwt);
        TestsUtils.validateUserPermissions(actualPermissions, userTestData.get(userId));

        Response userProfileResponse = RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + jwt)
                .when()
                .get(TestsUtils.userProfileEndPoint)
                .then()
                .statusCode(200).extract().response();

        String userProfileResponseBody = userProfileResponse.getBody().toString();

        TestsUtils.validateUserProfileEndPointResponse(userProfileResponseBody, userTestData.get(userId));
    }
}
