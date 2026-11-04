/*
 * @test
 * @summary Test for ZoneId.getLong(TemporalField)
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    public static void testGetLongThrowsException() {
        ZoneId zoneId = ZoneId.systemDefault();
        TemporalField dummyField = new TemporalField() {
            @Override
            public String getDisplayName(Locale locale) {
                return "dummy";
            }

            @Override
            public boolean isSupportedBy(TemporalAccessor temporal) {
                return false;
            }

            @Override
            public Class<?> getRangeType() {
                return Long.class;
            }

            @Override
            public String getDisplayName() {
                return "dummy";
            }
        };
        try {
            zoneId.getLong(dummyField);
            // If no exception, test fails
            System.err.println("Expected UnsupportedTemporalTypeException was not thrown.");
            System.exit(1);
        } catch (UnsupportedTemporalTypeException e) {
            // Expected exception
            System.out.println("Caught expected exception: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
