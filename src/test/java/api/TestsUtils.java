package api;

import TestCommons.UserPermissions;
import TestCommons.UserTestData;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsUtils {

    public static final String tokenEndPoint = "/token";
    public static final String userProfileEndPoint = "/profile";
    public static final String authRawString = "grant_type=client_credentials";
    public static final String authBase64String = "Z3JhbnRfdHlwZT1jbGllbnRfY3JlZGVudGlhbHM=";


    public static Map<String, Object> prepareRequestBody(UserTestData userTestData) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("login", userTestData.login);
        requestBody.put("password", userTestData.password);
        return requestBody;
    }

    public static void validateUserProfileEndPointResponse(String body, UserTestData expectedResults) {
        JSONObject obj = new JSONObject(body);
        assertEquals(expectedResults.expectedFirstName, obj.getString("firstName"));
        assertEquals(expectedResults.expectedLastName, obj.getString("lastName"));
        assertEquals(expectedResults.login, obj.getString("email"));
        assertEquals(expectedResults.expectedAge, Integer.parseInt(obj.getString("age")));
        assertFalse(obj.getString("profilePicture").isEmpty());
        assertEquals(expectedResults.expectedRole, obj.getString("userRole"));
    }

    public static void validateTokenEndPointResponse(Response response) {
        String accessToken = response.jsonPath().getString("access_token");
        try {
            String jwtPayload = Jwts.parser().parse(accessToken).getBody().toString();
        } catch (JwtException e) {
            fail("[INFO] Exception occurred: " + e.getMessage());
        }

        assertEquals(3600, Integer.parseInt(response.jsonPath().getString("expires_in")));
        assertEquals("bearer", response.jsonPath().getString("token_type"));
    }

    public static List<UserPermissions> extractUserPermissionsFromJWT(String jwt) {
        String jwtPayload = Jwts.parser().parse(jwt).getBody().toString();
        JSONObject obj = new JSONObject(jwtPayload);
        Iterator<Object> iterator = obj.getJSONArray("permission").iterator();
        List<UserPermissions> userPermissions = new ArrayList<>();

        while(iterator.hasNext()) {
            switch(iterator.next().toString()) {
                case "READ": userPermissions.add(UserPermissions.READ); break;
                case "CREATE": userPermissions.add(UserPermissions.CREATE); break;
                case "DELETE": userPermissions.add(UserPermissions.DELETE); break;
                case "BULK_CREATE": userPermissions.add(UserPermissions.BULK_CREATE); break;
                case "UPDATE": userPermissions.add(UserPermissions.UPDATE); break;
                default: fail("Unknown type of permission: " + iterator.next().toString()); break;
            }
        }

        return userPermissions;
    }

    public static void validateUserPermissions(List<UserPermissions> actualPermissions, UserTestData expectedPermissions) {
        assertEquals(expectedPermissions.expectedUserPermissions.size(), actualPermissions.size());
        assertTrue(expectedPermissions.expectedUserPermissions.containsAll(actualPermissions));
        assertTrue(actualPermissions.containsAll(expectedPermissions.expectedUserPermissions));
    }
}
