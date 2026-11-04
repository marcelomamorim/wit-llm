/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.appendValue method.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendValueWithNullField();
        testAppendValueWithInvalidWidth();
        testAppendValueWithValidInputs();
    }

    private static void testAppendValueWithNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendValue(null, 5);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testAppendValueWithInvalidWidth() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // width less than 1
        try {
            builder.appendValue(new DummyTemporalField(), 0);
            throw new AssertionError("Expected IllegalArgumentException for width=0 not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        // width greater than 19
        try {
            builder.appendValue(new DummyTemporalField(), 20);
            throw new AssertionError("Expected IllegalArgumentException for width=20 not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testAppendValueWithValidInputs() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendValue(new DummyTemporalField(), 10);
            // No exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static class DummyTemporalField implements TemporalField {
        @Override
        public String toString() {
            return "DummyTemporalField";
        }
        // Implement all other methods as no-op or throw UnsupportedOperationException
        @Override public String getName() { return "Dummy"; }
        @Override public boolean isSupportedBy(TemporalAccessor temporal) { return false; }
        @Override public ValueRange rangeRefinedBy(TemporalAccessor temporal) { return null; }
        @Override public long getFrom(TemporalAccessor temporal) { return 0; }
        @Override public <R extends java.lang.Object> R adjustInto(R temporal, long newValue) { throw new UnsupportedOperationException(); }
        @Override public String getDisplayName(java.util.Locale locale) { return "Dummy"; }
    }
}
