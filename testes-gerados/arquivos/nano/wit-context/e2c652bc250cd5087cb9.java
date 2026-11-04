/*
 * @test
 * @summary Tests for java.time.LocalTime.format
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testFormatWithNullFormatter();
        testFormatWithValidFormatter();
    }

    public static void testFormatWithNullFormatter() {
        LocalTime time = LocalTime.of(12, 30, 45);
        try {
            time.format(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testFormatWithValidFormatter() {
        LocalTime time = LocalTime.of(12, 30, 45);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String result = time.format(formatter);
        if (!"12:30:45".equals(result)) {
            System.err.println("Unexpected formatting result: " + result);
            System.exit(1);
        }
    }
}
