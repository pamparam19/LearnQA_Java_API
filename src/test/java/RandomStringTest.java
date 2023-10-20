import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

public class RandomStringTest {
    static final String ABC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    static SecureRandom rnd = new SecureRandom();
    int min = 13;
    int max = 17;
    int length = rnd.nextInt(max - min + 1) + min;

    // генерирует случайную строку длиной length
    static String randomStringGen(int length){
        StringBuilder sb = new StringBuilder(length);
        for(int i=0; i<length; i++){
            sb.append(ABC.charAt(rnd.nextInt(ABC.length())));
        }
        return sb.toString();
    }
    String testString = randomStringGen(length); // случайная строка длиной от min до max

    @Test
    public void testStringLength(){
        Assertions.assertTrue(testString.length()>15, "The string \"" +
                testString + "\" is shorter than 15 symbols");
    }

}
