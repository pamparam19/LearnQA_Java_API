import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CheckTokenTest {

    @Test
    public void testCheckToken() throws InterruptedException {
        String url = "https://playground.learnqa.ru/ajax/api/longtime_job";
        JsonPath response = RestAssured
                .get(url)
                .jsonPath();

        Map<String, String> params = new HashMap<>();
        params.put("token", response.get("token"));
        int time = response.getInt("seconds");

        JsonPath responseBeforeReady = RestAssured
                .given()
                .queryParams(params)
                .get(url)
                .jsonPath();
        String statusBeforeReady = responseBeforeReady.get("status");
        System.out.println(statusBeforeReady);
        if (statusBeforeReady.equals("Job is NOT ready")) {
            System.out.println("The status is correct");
        } else {
            System.out.println("Something's wrong");
        }

        System.out.println("Waiting for " + time + " seconds...");
        Thread.sleep(time * 1000L);

        JsonPath responseAfterReady = RestAssured
                .given()
                .queryParams(params)
                .get(url)
                .jsonPath();
        String statusAfterReady = responseAfterReady.get("status");
        String resultAfterReady = responseAfterReady.get("result");
        System.out.println(statusAfterReady);
        System.out.println("result is " + resultAfterReady);
        if (statusAfterReady.equals("Job is ready") && resultAfterReady != null && !resultAfterReady.isEmpty()) {
            System.out.println("Everything's correct");
        } else {
            System.out.println("Something's wrong");
        }
    }
}
