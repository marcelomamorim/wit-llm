/*
 * @test
 * @summary Test of DateTimeFormatter.ofLocalizedDate with various FormatStyle values
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testOfLocalizedDateWithFull();
        testOfLocalizedDateWithLong();
        testOfLocalizedDateWithMedium();
        testOfLocalizedDateWithShort();
    }

    static void testOfLocalizedDateWithFull() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        assert formatter != null : "Formatter with FULL style should not be null";
    }

    static void testOfLocalizedDateWithLong() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        assert formatter != null : "Formatter with LONG style should not be null";
    }

    static void testOfLocalizedDateWithMedium() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        assert formatter != null : "Formatter with MEDIUM style should not be null";
    }

    static void testOfLocalizedDateWithShort() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        assert formatter != null : "Formatter with SHORT style should not be null";
    }
}
