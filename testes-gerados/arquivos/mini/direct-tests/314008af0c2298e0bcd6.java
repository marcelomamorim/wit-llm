/*
 * @test
 * @summary Test java.time.Year.adjustInto(Temporal) method behavior
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ChronoField;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testAdjustIntoWithIsoChronology();
        testAdjustIntoWithNonIsoChronology();
    }

    private static void testAdjustIntoWithIsoChronology() {
        Year y = Year.of(2000);
        Temporal temporal = new Temporal() {
            private int year = 1990;

            @Override
            public boolean isSupported(TemporalField field) {
                return field == ChronoField.YEAR;
            }

            @Override
            public long getLong(TemporalField field) {
                if (field == ChronoField.YEAR) {
                    return year;
                }
                throw new UnsupportedOperationException();
            }

            @Override
            public Temporal with(TemporalField field, long newValue) {
                if (field == ChronoField.YEAR) {
                    year = (int) newValue;
                    return this;
                }
                throw new UnsupportedOperationException();
            }

            @Override
            public long until(Temporal endExclusive, TemporalUnit unit) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Chronology query(java.time.temporal.TemporalQuery<Chronology> query) {
                if (query == java.time.temporal.TemporalQueries.chronology()) {
                    return IsoChronology.INSTANCE;
                }
                return null;
            }
        };

        Temporal result = y.adjustInto(temporal);
        if (result != temporal) {
            throw new AssertionError("adjustInto should return the same Temporal instance");
        }
        long adjustedYear = temporal.getLong(ChronoField.YEAR);
        if (adjustedYear != 2000) {
            throw new AssertionError("Year was not adjusted correctly, expected 2000 but got " + adjustedYear);
        }
    }

    private static void testAdjustIntoWithNonIsoChronology() {
        Year y = Year.of(2000);
        Temporal temporal = new Temporal() {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }

            @Override
            public long getLong(TemporalField field) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Temporal with(TemporalField field, long newValue) {
                throw new UnsupportedOperationException();
            }

            @Override
            public long until(Temporal endExclusive, TemporalUnit unit) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Chronology query(java.time.temporal.TemporalQuery<Chronology> query) {
                if (query == java.time.temporal.TemporalQueries.chronology()) {
                    return new Chronology() {
                        @Override
                        public String getId() {
                            return "NonIso";
                        }

                        @Override
                        public String getCalendarType() {
                            return "noniso";
                        }
                    };
                }
                return null;
            }
        };

        try {
            y.adjustInto(temporal);
            throw new AssertionError("Expected DateTimeException for non-ISO chronology");
        } catch (DateTimeException ex) {
            // expected
        }
    }
}
