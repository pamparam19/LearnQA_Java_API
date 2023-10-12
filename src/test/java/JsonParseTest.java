import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class JsonParseTest {
    @Test
    public void jsonParseTest(){
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        //Находим дату создания второго сообщения
        List<String> allDates = response.getList(("messages.timestamp"));
        Collections.sort(allDates);
        Timestamp date = Timestamp.valueOf(allDates.get(1));


        //Находим сообщение, созданное во вторую по порядку дату
        int s = response.getInt("messages.size()");
        for(int i=0; i<s; i++) {
            if(Timestamp.valueOf(response.getString("messages["+i+"].timestamp")).equals(date)){
                System.out.println(response.getString("messages["+i+"].message"));
                break;
            }
        }
    }
}
