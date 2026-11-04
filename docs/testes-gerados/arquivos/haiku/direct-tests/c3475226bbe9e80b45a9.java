/*
 * @test
 * @summary Basic observable behavior of HijrahDate.of with valid and invalid arguments
 * @run main HijrahDateWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        HijrahDate d = HijrahDate.of(1438, 1, 1);
        check(d != null, "of returned null for valid arguments");
        check(d.equals(HijrahDate.of(1438, 1, 1)), "same arguments should produce equal dates");
        check(!d.equals(HijrahDate.of(1438, 1, 2)), "different day should produce a different date");

        expectDateTimeException(() -> HijrahDate.of(1438, 13, 1), "month 13");
        expectDateTimeException(() -> HijrahDate.of(1438, 0, 1), "month 0");
        expectDateTimeException(() -> HijrahDate.of(1438, 1, 0), "day 0");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectDateTimeException(Runnable action, String description) {
        try {
            action.run();
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for " + description);
    }
}
