/*
 * @test
 * @summary Test java.time.ZoneId.getLong(TemporalField) throws UnsupportedTemporalTypeException
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class ZoneIdWitupTest {
    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        TemporalField dummyField = new TemporalField() {
            @Override
            public boolean isSupportedBy(java.time.temporal.TemporalAccessor temporal) {
                return false;
            }
            @Override
            public long getFrom(java.time.temporal.TemporalAccessor temporal) {
                return 0;
            }
            @Override
            public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) {
                return temporal;
            }
            @Override
            public String getName() {
                return "DummyField";
            }
            @Override
            public TemporalUnit getBaseUnit() {
                return null;
            }
            @Override
            public TemporalUnit getRangeUnit() {
                return null;
            }
            @Override
            public ValueRange range() {
                return null;
            }
            @Override
            public boolean isDateBased() {
                return false;
            }
            @Override
            public boolean isTimeBased() {
                return false;
            }
            @Override
            public ValueRange rangeRefinedBy(java.time.temporal.TemporalAccessor temporal) {
                return null;
            }
        };

        try {
            zoneId.getLong(dummyField);
            throw new RuntimeException("Expected UnsupportedTemporalTypeException not thrown");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
