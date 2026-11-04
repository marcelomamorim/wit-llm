/*
 * @test
 * @summary Test java.time.Year.adjustInto(Temporal) method behavior
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.chrono.IsoChronology;
import java.time.chrono.Chronology;
import java.time.DateTimeException;
import java.time.LocalDate;

public class YearWitupTest {
    public static void main(String[] args) throws Exception {
        testAdjustIntoWithNull();
        testAdjustIntoWithNonIsoChronology();
        testAdjustIntoWithValidTemporal();
    }

    private static void testAdjustIntoWithNull() {
        Year y = Year.of(2000);
        try {
            y.adjustInto(null);
            throw new AssertionError("Expected NullPointerException for null temporal");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAdjustIntoWithNonIsoChronology() {
        Year y = Year.of(2000);
        Temporal temporal = new Temporal() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }

            @Override
            public long getLong(java.time.temporal.TemporalField field) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Temporal with(java.time.temporal.TemporalField field, long newValue) {
                return this;
            }

            @Override
            public Temporal plus(long amountToAdd, java.time.temporal.TemporalUnit unit) {
                return this;
            }

            @Override
            public <R> R query(java.time.temporal.TemporalQuery<R> query) {
                if (query == java.time.temporal.TemporalQueries.chronology()) {
                    return (R) Chronology.ofLocale(java.util.Locale.FRANCE); // Non-ISO chronology
                }
                return null;
            }
        };
        try {
            y.adjustInto(temporal);
            throw new AssertionError("Expected DateTimeException for non-ISO chronology");
        } catch (DateTimeException expected) {
            // Expected exception
        }
    }

    private static void testAdjustIntoWithValidTemporal() {
        Year y = Year.of(2020);
        LocalDate date = LocalDate.of(2010, 1, 1);
        Temporal result = y.adjustInto(date);
        if (!(result instanceof LocalDate)) {
            throw new AssertionError("Result should be a LocalDate");
        }
        LocalDate adjusted = (LocalDate) result;
        if (adjusted.getYear() != 2020) {
            throw new AssertionError("Year was not adjusted correctly");
        }
        if (adjusted.getMonthValue() != 1 || adjusted.getDayOfMonth() != 1) {
            throw new AssertionError("Month and day should remain unchanged");
        }
    }
}
