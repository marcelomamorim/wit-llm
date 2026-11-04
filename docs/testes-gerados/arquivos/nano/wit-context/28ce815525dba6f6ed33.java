/*
 * @test
 * @summary Test for IsoChronology.dateYearDay(int, int) method.
 * @run main IsoChronologyWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testProlepticYearTooSmall();
        testProlepticYearTooLarge();
        testDayOfYearTooLarge();
        testDayOfYearTooSmall();
    }

    static void testValidInputs() {
        try {
            int prolepticYear = 2020;
            int dayOfYear = 60;
            LocalDate date = IsoChronology.INSTANCE.dateYearDay(prolepticYear, dayOfYear);
            if (date == null || !date.equals(LocalDate.ofYearDay(prolepticYear, dayOfYear))) {
                throw new AssertionError("Valid input test failed");
            }
        } catch (Exception e) {
            throw new AssertionError("Valid input caused exception", e);
        }
    }

    static void testProlepticYearTooSmall() {
        try {
            int prolepticYear = Integer.MIN_VALUE + 1; // minimal valid proleptic year
            int dayOfYear = 1;
            // Assuming the method throws DateTimeException for invalid prolepticYear
            IsoChronology.INSTANCE.dateYearDay(prolepticYear - 100000, dayOfYear);
            throw new AssertionError("Expected DateTimeException for too small prolepticYear");
        } catch (java.time.DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for too small prolepticYear", e);
        }
    }

    static void testProlepticYearTooLarge() {
        try {
            int prolepticYear = Integer.MAX_VALUE - 1; // maximal valid proleptic year
            int dayOfYear = 1;
            // Assuming the method throws DateTimeException for invalid prolepticYear
            IsoChronology.INSTANCE.dateYearDay(prolepticYear + 100000, dayOfYear);
            throw new AssertionError("Expected DateTimeException for too large prolepticYear");
        } catch (java.time.DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for too large prolepticYear", e);
        }
    }

    static void testDayOfYearTooLarge() {
        try {
            int prolepticYear = 2020;
            int dayOfYear = 367; // invalid for leap year
            IsoChronology.INSTANCE.dateYearDay(prolepticYear, dayOfYear);
            throw new AssertionError("Expected DateTimeException for dayOfYear too large");
        } catch (java.time.DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for dayOfYear too large", e);
        }
    }

    static void testDayOfYearTooSmall() {
        try {
            int prolepticYear = 2020;
            int dayOfYear = 0; // invalid
            IsoChronology.INSTANCE.dateYearDay(prolepticYear, dayOfYear);
            throw new AssertionError("Expected DateTimeException for dayOfYear too small");
        } catch (java.time.DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for dayOfYear too small", e);
        }
    }
}
