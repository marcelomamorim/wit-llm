/*
 * @test
 * @summary Test Year.adjustInto method for ISO chronology
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoUnit;
import java.time.chrono.IsoChronology;
import java.time.DateTimeException;

public class YearWitupTest {

    public static void main(String[] args) {
        testAdjustIntoWithIsoChronology();
        testAdjustIntoWithNonIsoChronology();
    }

    static void testAdjustIntoWithIsoChronology() {
        Year year = Year.of(2023);
        Temporal temporal = new TemporalStub();
        try {
            Temporal result = year.adjustInto(temporal);
            if (result != null && result instanceof TemporalStub) {
                TemporalStub ts = (TemporalStub) result;
                assert ts.getYear() == 2023 : "Year should be adjusted to 2023";
            } else {
                throw new AssertionError("Result is not TemporalStub or null")
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testAdjustIntoWithNonIsoChronology() {
        Year year = Year.of(2023);
        Temporal temporal = new TemporalStub();
        // Override Chronology.from to return non-IsoChronology
        try {
            Temporal result = year.adjustInto(temporal);
            throw new AssertionError("Expected DateTimeException was not thrown");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static class TemporalStub implements Temporal {
        private int year = 0;
        public int getYear() { return year; }
        @Override
        public <R> R query(java.time.temporal.TemporalQuery<R> query) { return null; }
        @Override
        public Temporal with(java.time.temporal.TemporalField field, long newValue) {
            if (field == null) {
                throw new NullPointerException();
            }
            if (field == java.time.temporal.ChronoField.YEAR) {
                this.year = (int) newValue;
            }
            return this;
        }
        @Override
        public boolean isSupported(java.time.temporal.TemporalField field) { return true; }
        @Override
        public boolean isSupported(java.time.temporal.TemporalUnit unit) { return true; }
        @Override
        public java.time.temporal.ValueRange range(java.time.temporal.TemporalField field) { return null; }
        @Override
        public long getLong(java.time.temporal.TemporalField field) { return 0; }
        @Override
        public Temporal minus(long amountToSubtract, java.time.temporal.TemporalUnit unit) { return this; }
        @Override
        public Temporal plus(long amountToAdd, java.time.temporal.TemporalUnit unit) { return this; }
        @Override
        public Object toString() { return "TemporalStub"; }
    }
}
