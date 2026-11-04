/*
 * @test
 * @summary Test java.time.YearMonth.format(DateTimeFormatter) method
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        YearMonth ym = YearMonth.of(2020, 5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String result = ym.format(formatter);
        if (!"2020-05".equals(result)) {
            throw new AssertionError("Expected '2020-05' but got '" + result + "'");
        }
    }

    private static void testFormatWithNullFormatter() {
        YearMonth ym = YearMonth.of(2020, 5);
        try {
            ym.format(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
