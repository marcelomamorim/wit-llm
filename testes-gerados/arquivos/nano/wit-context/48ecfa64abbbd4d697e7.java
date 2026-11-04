/*
 * @test
 * @summary Test for java.time.LocalDate.from(TemporalAccessor)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.DateTimeException;
import java.util.Objects;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testFromWithNull();
        testFromWithValidTemporal();
        testFromWithInvalidTemporal();
    }

    static void testFromWithNull() {
        try {
            LocalDate.from(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testFromWithValidTemporal() {
        // Using a known LocalDate as TemporalAccessor
        LocalDate date = LocalDate.of(2023, 3, 15);
        TemporalAccessor ta = date;
        LocalDate result = LocalDate.from(ta);
        if (!result.equals(date)) {
            System.err.println("Failed: Expected " + date + " but got " + result);
        }
    }

    static void testFromWithInvalidTemporal() {
        // Creating a dummy TemporalAccessor that returns null for query
        TemporalAccessor ta = new TemporalAccessor() {
            @Override
            public <R> R query(java.time.temporal.TemporalQuery<R> query) {
                return null;
            }
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }
        };
        try {
            LocalDate.from(ta);
            System.err.println("Failed: Expected DateTimeException for invalid TemporalAccessor");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
