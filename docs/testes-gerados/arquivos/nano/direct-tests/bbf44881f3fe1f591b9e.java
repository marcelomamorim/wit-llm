/*
 * @test
 * @summary Test for LocalTime.withHour(int)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testWithHourSameValue();
        testWithHourValidValue();
        testWithHourInvalidValue();
    }

    static void testWithHourSameValue() {
        LocalTime time = LocalTime.of(10, 30, 45, 123456789);
        LocalTime result = time.withHour(10);
        assert result == time : "Expected same instance when hour is unchanged";
    }

    static void testWithHourValidValue() {
        LocalTime time = LocalTime.of(10, 30, 45, 123456789);
        LocalTime result = time.withHour(15);
        assert result.getHour() == 15 : "Hour should be updated to 15";
        assert result.getMinute() == time.getMinute() : "Minutes should remain unchanged";
        assert result.getSecond() == time.getSecond() : "Seconds should remain unchanged";
        assert result.getNano() == time.getNano() : "Nano should remain unchanged";
    }

    static void testWithHourInvalidValue() {
        LocalTime time = LocalTime.of(10, 30);
        boolean exceptionThrown = false;
        try {
            time.withHour(25);
        } catch (DateTimeException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected DateTimeException for invalid hour";
    }
}
