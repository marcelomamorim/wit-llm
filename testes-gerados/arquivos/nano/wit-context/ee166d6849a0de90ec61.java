/*
 * @test
 * @summary Test for ZoneId.getLong method, covering exception path
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.TemporalField;

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    private static void testGetLongThrowsException() {
        ZoneId zoneId = ZoneId.systemDefault();
        TemporalField fakeField = new TemporalField() {
            @Override
            public String getDisplayName(Locale locale) {
                return "FakeField";
            }
            @Override
            public Class<?> getRangeType() {
                return null;
            }
            @Override
            public String getBaseUnit() {
                return null;
            }
            @Override
            public boolean isSupportedBy(TemporalAccessor temporal) {
                return false;
            }
            @Override
            public <R extends Temporal> R adjustInto(R temporal, long newValue) {
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
            public boolean isSupportedBy(TemporalAccessor temporal) {
                return false;
            }
            @Override
            public <R extends Temporal> R adjustInto(R temporal, long newValue) {
                return null;
            }
            @Override
            public String toString() {
                return "FakeField";
            }
        };
        try {
            zoneId.getLong(fakeField);
            // Fail if no exception thrown
            throw new AssertionError("Expected UnsupportedTemporalTypeException was not thrown");
        } catch (UnsupportedTemporalTypeException e) {
            // Expected exception
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
