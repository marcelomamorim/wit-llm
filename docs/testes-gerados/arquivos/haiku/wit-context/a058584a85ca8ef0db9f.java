/*
 * @test
 * @summary Witup-derived checks for DateTimeFormatter.ofPattern(String, Locale) null and valid inputs
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatterWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    private static void expectNpe(Runnable action, String message) {
        try {
            action.run();
            failures++;
            System.err.println("FAIL (no exception): " + message);
        } catch (NullPointerException expected) {
            // expected
        } catch (RuntimeException other) {
            failures++;
            System.err.println("FAIL (wrong exception " + other + "): " + message);
        }
    }

    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu/MM/dd", Locale.US);
        check("2021/03/04".equals(f.format(LocalDate.of(2021, 3, 4))),
              "valid pattern should format date");

        DateTimeFormatter g = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        check("2020-01-02".equals(g.format(LocalDate.of(2020, 1, 2))),
              "yyyy-MM-dd should format date");

        expectNpe(() -> DateTimeFormatter.ofPattern(null, Locale.US),
                  "null pattern must throw NullPointerException");

        expectNpe(() -> DateTimeFormatter.ofPattern("", null),
                  "null locale with empty pattern must throw NullPointerException");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
