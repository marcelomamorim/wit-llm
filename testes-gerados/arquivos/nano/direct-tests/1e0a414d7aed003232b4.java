/*
 * @test
 * @summary Test LocalTime.withMinute(int) method for correctness and regressions
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testWithMinuteSameValue();
        testWithMinuteValidChange();
        testWithMinuteInvalidNegative();
        testWithMinuteInvalidTooLarge();
    }

    static void testWithMinuteSameValue() {
        LocalTime time = LocalTime.of(10, 30, 15, 500_000_000);
        LocalTime result = time.withMinute(30);
        assert result == time : "Expected same instance when minute is unchanged";
    }

    static void testWithMinuteValidChange() {
        LocalTime time = LocalTime.of(10, 30, 15, 500_000_000);
        LocalTime result = time.withMinute(45);
        assert result.getHour() == 10 : "Hour should remain unchanged";
        assert result.getMinute() == 45 : "Minute should be updated";
        assert result.getSecond() == 15 : "Second should remain unchanged";
        assert result.getNano() == 500_000_000 : "Nano should remain unchanged";
    }

    static void testWithMinuteInvalidNegative() {
        try {
            LocalTime.of(10, 30).withMinute(-1);
            assert false : "Expected DateTimeException for negative minute";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testWithMinuteInvalidTooLarge() {
        try {
            LocalTime.of(10, 30).withMinute(60);
            assert false : "Expected DateTimeException for minute >= 60";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
