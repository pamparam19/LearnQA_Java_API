package tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeadersAssertTest {

    @Test
    public void testHeadersAssert(){
        Response responseWithHeaders = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers headers = responseWithHeaders.getHeaders();

        assertTrue(headers.hasHeaderWithName("x-secret-homework-header"), "Response doesn't contain secret header");
        assertEquals("Some secret value", headers.getValue("x-secret-homework-header"), "Response" +
                "doesn't contain header with the secret value");

    }
}
