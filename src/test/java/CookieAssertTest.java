import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CookieAssertTest {

    @Test
    public void testCookieAssert(){
        Response responseWithCookie = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Map<String,String> responseCookies = responseWithCookie.getCookies();

        assertTrue(responseCookies.containsKey("HomeWork"), "Response doesn't contain 'HomeWork' cookie");
        assertTrue(responseCookies.containsValue("hw_value"), "Response cookie doesn't contain 'hw_value'");

    }
}
