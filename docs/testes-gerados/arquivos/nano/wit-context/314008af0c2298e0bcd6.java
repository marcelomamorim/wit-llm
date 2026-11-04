/*
 * @test
 * @summary Tests for java.time.Year.adjustInto(Temporal)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoUnit;
import java.time.DateTimeException;

public class YearWitupTest {

    public static void main(String[] args) {
        testAdjustIntoWithValidTemporal();
        testAdjustIntoWithNullTemporal();
    }

    static void testAdjustIntoWithValidTemporal() {
        try {
            Year year = Year.of(2023);
            Temporal temporal = new TemporalStub();
            Temporal result = year.adjustInto(temporal);
            if (result != null) {
                System.out.println("PASS: adjustInto with valid Temporal");
            } else {
                System.out.println("FAIL: adjustInto with valid Temporal returned null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: adjustInto with valid Temporal threw exception: " + e);
        }
    }

    static void testAdjustIntoWithNullTemporal() {
        try {
            Year year = Year.of(2023);
            year.adjustInto(null);
            System.out.println("FAIL: adjustInto with null Temporal did not throw");
        } catch (NullPointerException e) {
            System.out.println("PASS: adjustInto with null Temporal threw NullPointerException");
        } catch (Exception e) {
            System.out.println("FAIL: adjustInto with null Temporal threw wrong exception: " + e);
        }
    }

    static class TemporalStub implements Temporal {
        @Override
        public Temporal with(TemporalField field, long newValue) {
            return this;
        }
        @Override
        public boolean isSupported(TemporalField field) {
            return true;
        }
        @Override
        public long getLong(TemporalField field) {
            return 0;
        }
        @Override
        public <R> R query(TemporalQuery<R> query) {
            return null;
        }
        @Override
        public Temporal minus(long amountToSubtract, TemporalUnit unit) {
            return this;
        }
        @Override
        public Temporal minus(TemporalAmount amount) {
            return this;
        }
        @Override
        public Temporal plus(long amountToAdd, TemporalUnit unit) {
            return this;
        }
        @Override
        public Temporal plus(TemporalAmount amount) {
            return this;
        }
        @Override
        public boolean isSupported(java.time.temporal.TemporalUnit unit) {
            return true;
        }
        @Override
        public <R> R query(java.time.temporal.TemporalQuery<R> query) {
            return null;
        }
        @Override
        public long until(Temporal endExclusive, TemporalUnit unit) {
            return 0;
        }
        @Override
        public Temporal with(TemporalAdjuster adjuster) {
            return this;
        }
        @Override
        public <R> R query(java.time.temporal.TemporalQuery<R> query) {
            return null;
        }
        @Override
        public boolean isSupported(java.time.temporal.TemporalField field) {
            return true;
        }
        @Override
        public long getLong(java.time.temporal.TemporalField field) {
            return 0;
        }
    }
}
