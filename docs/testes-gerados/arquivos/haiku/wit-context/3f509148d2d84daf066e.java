/*
 * @test
 * @summary WITUP-derived checks for WeekFields.of(Locale): null locale must throw NPE and US locale must match the Sunday/minimal-days-1 factory
 * @run main WeekFieldsWitupTest
 */

import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekFieldsWitupTest {

    public static void main(String[] args) {
        testNullLocaleThrowsNPE();
        testUsLocaleMatchesSundayMinimalDaysOne();
        testFactoryIsDeterministicForSameLocale();
    }

    static void testNullLocaleThrowsNPE() {
        boolean thrown = false;
        try {
            WeekFields.of((Locale) null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }

    static void testUsLocaleMatchesSundayMinimalDaysOne() {
        WeekFields us = WeekFields.of(Locale.US);
        WeekFields expected = WeekFields.of(DayOfWeek.SUNDAY, 1);
        if (!us.equals(expected)) {
            throw new RuntimeException("WeekFields.of(Locale.US) should equal WeekFields.of(SUNDAY, 1): " + us);
        }
    }

    static void testFactoryIsDeterministicForSameLocale() {
        WeekFields first = WeekFields.of(Locale.US);
        WeekFields second = WeekFields.of(Locale.US);
        if (!first.equals(second)) {
            throw new RuntimeException("Repeated WeekFields.of(Locale.US) calls should be equal");
        }
        if (first.hashCode() != second.hashCode()) {
            throw new RuntimeException("Equal WeekFields instances should have equal hashCode");
        }
    }
}
