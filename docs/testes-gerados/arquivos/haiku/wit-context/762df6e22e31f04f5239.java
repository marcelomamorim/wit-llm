/*
 * @test
 * @summary Witup-generated tests for YearMonth.parse(CharSequence, DateTimeFormatter)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM");

        YearMonth parsed = YearMonth.parse("2012-07", formatter);
        if (!parsed.equals(YearMonth.of(2012, 7))) {
            fail("expected 2012-07 but got " + parsed);
        }

        expectNullPointerException(() -> YearMonth.parse("2012-07", null));
        expectNullPointerException(() -> YearMonth.parse(null, formatter));
        expectDateTimeParseException(() -> YearMonth.parse("2012-7x", formatter));
    }

    private interface Action {
        void run();
    }

    private static void expectNullPointerException(Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        fail("expected NullPointerException");
    }

    private static void expectDateTimeParseException(Action action) {
        try {
            action.run();
        } catch (DateTimeParseException expected) {
            return;
        }
        fail("expected DateTimeParseException");
    }

    private static void fail(String message) {
        throw new RuntimeException(message);
    }
}
