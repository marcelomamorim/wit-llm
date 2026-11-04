/*
 * @test
 * @summary Witup-derived tests for Month.getDisplayName
 * @run main MonthWitupTest
 */

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthWitupTest {

    interface Action {
        void run();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNpe(String message, Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException: " + message);
    }

    public static void main(String[] args) {
        check(Month.JANUARY.getDisplayName(TextStyle.FULL, Locale.US).equals("January"),
              "FULL January");
        check(Month.JANUARY.getDisplayName(TextStyle.SHORT, Locale.US).equals("Jan"),
              "SHORT January");
        check(Month.JANUARY.getDisplayName(TextStyle.NARROW, Locale.US).equals("J"),
              "NARROW January");
        check(Month.DECEMBER.getDisplayName(TextStyle.FULL, Locale.US).equals("December"),
              "FULL December");
        check(Month.DECEMBER.getDisplayName(TextStyle.SHORT, Locale.US).equals("Dec"),
              "SHORT December");
        check(Month.of(7).getDisplayName(TextStyle.FULL, Locale.US).equals("July"),
              "FULL July via of");
        check(Month.from(Month.MARCH).getDisplayName(TextStyle.SHORT, Locale.US).equals("Mar"),
              "SHORT March via from");

        expectNpe("null style", () -> Month.JANUARY.getDisplayName(null, Locale.US));
        expectNpe("null locale", () -> Month.JANUARY.getDisplayName(TextStyle.FULL, null));
    }
}
