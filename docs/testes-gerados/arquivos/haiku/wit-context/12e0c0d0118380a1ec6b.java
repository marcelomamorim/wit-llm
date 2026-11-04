/*
 * @test
 * @summary WITUP-derived checks for DateTimeFormatterBuilder.appendValue(TemporalField, int) argument validation
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        testNullFieldThrowsNPE(builder);
        testNullFieldWithInvalidWidthThrowsNPE(builder);
        testWidthBelowRangeThrowsIAE(builder);
        testWidthAboveRangeThrowsIAE(builder);
        testBoundaryWidthsAreAccepted(builder);
    }

    static void testNullFieldThrowsNPE(DateTimeFormatterBuilder builder) {
        try {
            builder.appendValue((TemporalField) null, 4);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null field");
    }

    static void testNullFieldWithInvalidWidthThrowsNPE(DateTimeFormatterBuilder builder) {
        try {
            builder.appendValue((TemporalField) null, 0);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null field checked before width");
    }

    static void testWidthBelowRangeThrowsIAE(DateTimeFormatterBuilder builder) {
        expectIllegalArgument(builder, 0);
        expectIllegalArgument(builder, -1);
    }

    static void testWidthAboveRangeThrowsIAE(DateTimeFormatterBuilder builder) {
        expectIllegalArgument(builder, 20);
        expectIllegalArgument(builder, 100);
    }

    static void testBoundaryWidthsAreAccepted(DateTimeFormatterBuilder builder) {
        if (builder.appendValue(ChronoField.YEAR, 1) != builder) {
            throw new RuntimeException("appendValue with width 1 should return this builder");
        }
        if (builder.appendValue(ChronoField.YEAR, 19) != builder) {
            throw new RuntimeException("appendValue with width 19 should return this builder");
        }
    }

    static void expectIllegalArgument(DateTimeFormatterBuilder builder, int width) {
        try {
            builder.appendValue(ChronoField.YEAR, width);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for width " + width);
    }
}
