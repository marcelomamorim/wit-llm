/*
 * @test
 * @summary Witup-derived checks for Year.parse(CharSequence, DateTimeFormatter)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearWitupTest {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu");

        Year parsed = Year.parse("2012", formatter);
        if (parsed.getValue() != 2012) {
            throw new RuntimeException("expected 2012 but was " + parsed.getValue());
        }

        expectNullPointerException(() -> Year.parse("2012", null), "null formatter must throw NPE");
        expectNullPointerException(() -> Year.parse(null, formatter), "null text must throw NPE");
        expectNullPointerException(() -> Year.parse(null, null), "null text and formatter must throw NPE");

        expectDateTimeParseException(() -> Year.parse("abc", formatter), "invalid text must throw DateTimeParseException");
    }

    private interface Action {
        void run();
    }

    private static void expectNullPointerException(Action action, String message) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    private static void expectDateTimeParseException(Action action, String message) {
        try {
            action.run();
        } catch (DateTimeParseException e) {
            return;
        }
        throw new RuntimeException(message);
    }
}
