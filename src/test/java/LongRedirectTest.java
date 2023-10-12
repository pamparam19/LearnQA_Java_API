import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirectTest {

    @Test
    public void testLongRedirect() {

        int statusCode = 0;
        int count = 0;
        String url = "https://playground.learnqa.ru/api/long_redirect";
        while (statusCode != 200) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();

            url = response.getHeader("Location");
            statusCode = response.getStatusCode();
            if (url != null) {
                count++;
                System.out.println("Редирект " + count + ": " + url);
            }
        }
        System.out.println("Количество редиректов " + count);
    }
}
