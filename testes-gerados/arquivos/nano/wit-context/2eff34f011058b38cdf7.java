/*
 * @test
 * @summary Test for DateTimeFormatter.toFormat method, focusing on null input handling.
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQuery;
import java.text.Format;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testToFormatNullInput();
    }

    public static void testToFormatNullInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.toFormat(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null input");
        }
    }
}
