/*
 * @test
 * @summary Deterministic tests for java.time.temporal.WeekFields.of(Locale)
 * @run main WeekFieldsWitupTest
 */

import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Objects;

public class WeekFieldsWitupTest {
    public static void main(String[] args) {
        testOfWithValidLocale();
        testOfWithNullLocale();
    }

    private static void testOfWithValidLocale() {
        Locale locale = Locale.US;
        WeekFields wf = WeekFields.of(locale);
        Objects.requireNonNull(wf);
        // The first day of week for Locale.US is Sunday (7th day), minimal days in first week is usually 1
        // We check that the returned WeekFields has firstDayOfWeek Sunday
        if (wf.getFirstDayOfWeek() != java.time.DayOfWeek.SUNDAY) {
            throw new AssertionError("Expected first day of week to be SUNDAY for Locale.US");
        }
        // minimalDaysInFirstWeek is at least 1
        int minDays = wf.getMinimalDaysInFirstWeek();
        if (minDays < 1 || minDays > 7) {
            throw new AssertionError("Minimal days in first week out of range: " + minDays);
        }
    }

    private static void testOfWithNullLocale() {
        try {
            WeekFields.of(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"locale".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
