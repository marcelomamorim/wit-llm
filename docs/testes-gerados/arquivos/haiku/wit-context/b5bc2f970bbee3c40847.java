/*
 * @test
 * @summary Witup-derived tests for MonthDay.parse(CharSequence, DateTimeFormatter) null checks and parsing behavior
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MonthDayWitupTest {

    interface Action {
        void run();
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        MonthDay parsed = MonthDay.parse("12-25", formatter);
        if (!parsed.equals(MonthDay.of(12, 25))) {
            throw new AssertionError("Unexpected parse result: " + parsed);
        }

        expectNullPointerException(() -> MonthDay.parse("12-25", null));
        expectNullPointerException(() -> MonthDay.parse(null, formatter));
        expectNullPointerException(() -> MonthDay.parse(null, null));

        expectDateTimeParseException(() -> MonthDay.parse("13-01", formatter));
    }

    static void expectNullPointerException(Action action) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("Expected NullPointerException");
    }

    static void expectDateTimeParseException(Action action) {
        try {
            action.run();
        } catch (DateTimeParseException e) {
            return;
        }
        throw new AssertionError("Expected DateTimeParseException");
    }
}
