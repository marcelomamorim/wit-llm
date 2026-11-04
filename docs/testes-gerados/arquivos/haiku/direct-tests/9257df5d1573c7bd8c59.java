/*
 * @test
 * @summary Verifies HijrahEra.getDisplayName for valid styles and locales, and null argument handling
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class HijrahEraWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void checkNpe(Runnable r, String message) {
        try {
            r.run();
            failures++;
            System.err.println("FAILED (no exception): " + message);
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        HijrahEra era = HijrahEra.of(1);

        String full = era.getDisplayName(TextStyle.FULL, Locale.US);
        String····short_ = era.getDisplayName(TextStyle.SHORT, Locale.US);
        String narrow = era.getDisplayName(TextStyle.NARROW, Locale.US);

        check(full != null && !full.isEmpty(), "FULL display name must be non-empty");
        check(short_ != null && !short_.isEmpty(), "SHORT display name must be non-empty");
        check(narrow != null && !narrow.isEmpty(), "NARROW display name must be non-empty");

        check(full.equals(era.getDisplayName(TextStyle.FULL, Locale.US)),
              "getDisplayName must be deterministic for FULL/US");
        check(short_.equals(era.getDisplayName(TextStyle.SHORT, Locale.US)),
              "getDisplayName must be deterministic for SHORT/US");

        String root = era.getDisplayName(TextStyle.SHORT, Locale.ROOT);
        check(root != null && !root.isEmpty(), "display name with Locale.ROOT must be non-empty");

        checkNpe(() -> era.getDisplayName(null, Locale.US), "null style must throw NullPointerException");
        checkNpe(() -> era.getDisplayName(TextStyle.FULL, null), "null locale must throw NullPointerException");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
