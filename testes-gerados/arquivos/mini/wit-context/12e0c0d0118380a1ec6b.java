/*
 * @test
 * @summary Test appendValue(TemporalField, int) method of DateTimeFormatterBuilder for argument validation
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testNullFieldThrowsNPE();
        testWidthLessThanOneThrowsIAE();
        testWidthGreaterThan19ThrowsIAE();
        testValidAppendValueReturnsBuilder();
        System.out.println("All tests passed.");
    }

    private static void testNullFieldThrowsNPE() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendValue(null, 1);
            throw new AssertionError("Expected NullPointerException for null field");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testWidthLessThanOneThrowsIAE() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.DAY_OF_MONTH;
        try {
            builder.appendValue(field, 0);
            throw new AssertionError("Expected IllegalArgumentException for width < 1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testWidthGreaterThan19ThrowsIAE() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.DAY_OF_MONTH;
        try {
            builder.appendValue(field, 20);
            throw new AssertionError("Expected IllegalArgumentException for width > 19");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidAppendValueReturnsBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.DAY_OF_MONTH;
        DateTimeFormatterBuilder result = builder.appendValue(field, 2);
        if (result != builder) {
            throw new AssertionError("appendValue should return the same builder instance");
        }
    }
}
