/*
 * @test
 * @summary Test LocalDate.from(TemporalAccessor) for various TemporalAccessor inputs
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ChronoField;
import java.time.DateTimeException;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidTemporalAccessor();
        testNullTemporalAccessor();
        testInvalidTemporalAccessor();
    }

    static void testValidTemporalAccessor() {
        LocalDate expectedDate = LocalDate.of(2023, 3, 15);
        TemporalAccessor ta = new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                return field == ChronoField.EPOCH_DAY;
            }

            @Override
            public long getLong(TemporalField field) {
                if (field == ChronoField.EPOCH_DAY) {
                    return expectedDate.toEpochDay();
                }
                throw new UnsupportedOperationException();
            }
        };
        LocalDate result = LocalDate.from(ta);
        assert result.equals(expectedDate) : "Expected " + expectedDate + " but got " + result;
    }

    static void testNullTemporalAccessor() {
        try {
            LocalDate.from(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidTemporalAccessor() {
        TemporalAccessor ta = new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }

            @Override
            public long getLong(TemporalField field) {
                throw new UnsupportedOperationException();
            }
        };
        try {
            LocalDate.from(ta);
            assert false : "Expected DateTimeException";
        } catch (DateTimeException e) {
            // expected
        }
    }
}
