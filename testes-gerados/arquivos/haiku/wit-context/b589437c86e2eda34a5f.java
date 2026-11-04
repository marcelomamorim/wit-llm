/*
 * @test
 * @summary Witup-derived tests for YearMonth.format(DateTimeFormatter)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNpe();
        testFormatUsesFormatter();
    }

    static void testNullFormatterThrowsNpe() {
        YearMonth ym = YearMonth.of(2020, 5);
        try {
            ym.format(null);
        } catch (NullPointerException e) {
            if (!"formatter".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("expected NullPointerException for null formatter");
    }

    static void testFormatUsesFormatter() {
        String s1 = YearMonth.of(2020, 5).format(DateTimeFormatter.ofPattern("uuuu-MM"));
        if (!"2020-05".equals(s1)) {
            throw new RuntimeException("expected 2020-05 but got " + s1);
        }
        String s2 = YearMonth.of(2020, 12).format(DateTimeFormatter.ofPattern("MM/uuuu"));
        if (!"12/2020".equals(s2)) {
            throw new RuntimeException("expected 12/2020 but got " + s2);
        }
    }
}
