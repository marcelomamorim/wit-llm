/*
 * @test
 * @summary Test IsoChronology.date(TemporalAccessor) method for various TemporalAccessor inputs.
 * @run main IsoChronologyWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testValidTemporalAccessor();
        testNullTemporalAccessor();
        testPartialTemporalAccessor();
        testInvalidTemporalAccessor();
    }

    static void testValidTemporalAccessor() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        try {
            LocalDate result = IsoChronology.date(date);
            assert result.equals(date) : "Expected date to be equal to input";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid TemporalAccessor", e);
        }
    }

    static void testNullTemporalAccessor() {
        try {
            IsoChronology.date((TemporalAccessor) null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null input", e);
        }
    }

    static void testPartialTemporalAccessor() {
        // Create a TemporalAccessor with only year and month, missing day
        TemporalAccessor partial = new TemporalAccessor() {
            @Override
            public boolean isSupported(ChronoField field) {
                return field == ChronoField.YEAR || field == ChronoField.MONTH_OF_YEAR;
            }

            @Override
            public long getLong(ChronoField field) {
                if (field == ChronoField.YEAR) return 2023;
                if (field == ChronoField.MONTH_OF_YEAR) return 3;
                throw new UnsupportedOperationException();
            }
        };
        try {
            LocalDate result = IsoChronology.date(partial);
            // Since day is missing, LocalDate.from should throw an exception
            throw new AssertionError("Expected exception for partial TemporalAccessor");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidTemporalAccessor() {
        // Create a TemporalAccessor with invalid month
        TemporalAccessor invalid = new TemporalAccessor() {
            @Override
            public boolean isSupported(ChronoField field) {
                return field == ChronoField.YEAR || field == ChronoField.MONTH_OF_YEAR || field == ChronoField.DAY_OF_MONTH;
            }

            @Override
            public long getLong(ChronoField field) {
                if (field == ChronoField.YEAR) return 2023;
                if (field == ChronoField.MONTH_OF_YEAR) return 13; // invalid month
                if (field == ChronoField.DAY_OF_MONTH) return 10;
                throw new UnsupportedOperationException();
            }
        };
        try {
            LocalDate result = IsoChronology.date(invalid);
            // LocalDate.from should throw an exception due to invalid month
            throw new AssertionError("Expected exception for invalid TemporalAccessor");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
