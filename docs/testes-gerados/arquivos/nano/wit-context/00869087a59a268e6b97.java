/* @test    @summary Test for MonthDay.format with null formatter to trigger NullPointerException    @run main MonthDayWitupTest */

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testFormatWithNullFormatter();
    }

    public static void testFormatWithNullFormatter() {
        MonthDay md = MonthDay.of(1, 1);
        try {
            md.format(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("NullPointerException correctly thrown.");
        }
    }
}
