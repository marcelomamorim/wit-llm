/*
 * @test
 * @summary LocalDate.withYear returns this for the same year, adjusts invalid leap days and rejects out-of-range years
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testSameYearReturnsThis();
        testChangeYearKeepsMonthAndDay();
        testLeapDayAdjustedToFebruary28();
        testLeapDayKeptWhenTargetYearIsLeap();
        testBoundaryYears();
        testOutOfRangeYearsThrow();
    }

    static void testSameYearReturnsThis() {
        LocalDate d = LocalDate.of(2020, 1, 15);
        if (d.withYear(2020) != d) {
            throw new RuntimeException("withYear with same year must return this");
        }
    }

    static void testChangeYearKeepsMonthAndDay() {
        LocalDate d = LocalDate.of(2020, 1, 15);
        LocalDate r = d.withYear(2021);
        if (!r.equals(LocalDate.of(2021, 1, 15))) {
            throw new RuntimeException("Expected 2021-01-15 but got " + r);
        }
    }

    static void testLeapDayAdjustedToFebruary28() {
        LocalDate leap = LocalDate.of(2000, 2, 29);
        LocalDate r = leap.withYear(2001);
        if (!r.equals(LocalDate.of(2001, 2, 28))) {
            throw new RuntimeException("Expected 2001-02-28 but got " + r);
        }
        LocalDate r1900 = leap.withYear(1900);
        if (!r1900.equals(LocalDate.of(1900, 2, 28))) {
            throw new RuntimeException("Expected 1900-02-28 but got " + r1900);
        }
    }

    static void testLeapDayKeptWhenTargetYearIsLeap() {
        LocalDate leap = LocalDate.of(2000, 2, 29);
        LocalDate r = leap.withYear(2004);
        if (!r.equals(LocalDate.of(2004, 2, 29))) {
            throw new RuntimeException("Expected 2004-02-29 but got " + r);
        }
    }

    static void testBoundaryYears() {
        LocalDate d = LocalDate.of(2020, 6, 10);
        LocalDate max = d.withYear(999999999);
        if (max.getYear() != 999999999 || max.getMonthValue() != 6 || max.getDayOfMonth() != 10) {
            throw new RuntimeException("Unexpected max year result: " + max);
        }
        LocalDate min = d.withYear(-999999999);
        if (min.getYear() != -999999999 || min.getMonthValue() != 6 || min.getDayOfMonth() != 10) {
            throw new RuntimeException("Unexpected min year result: " + min);
        }
    }

    static void testOutOfRangeYearsThrow() {
        LocalDate d = LocalDate.of(2020, 1, 15);
        try {
            d.withYear(1000000000);
            throw new RuntimeException("Expected DateTimeException for year 1000000000");
        } catch (DateTimeException expected) {
            // expected
        }
        try {
            d.withYear(-1000000000);
            throw new RuntimeException("Expected DateTimeException for year -1000000000");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
