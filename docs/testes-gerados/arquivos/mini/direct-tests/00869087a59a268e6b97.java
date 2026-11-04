/*
 * @test
 * @summary Test java.time.MonthDay.format(DateTimeFormatter) method
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class MonthDayWitupTest {
    public static void main(String[] args) throws Exception {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        MonthDay md = MonthDay.of(Month.JANUARY, 15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        String result = md.format(formatter);
        if (!"01-15".equals(result)) {
            throw new AssertionError("Expected '01-15' but got '" + result + "'");
        }
    }

    private static void testFormatWithNullFormatter() {
        MonthDay md = MonthDay.of(2, 28);
        try {
            md.format(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
