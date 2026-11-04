/*
 * @test
 * @summary Test LocalTime.withNano(int nanoOfSecond) method for correctness and regressions
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testWithNanoSameValue();
        testWithNanoDifferentValue();
        testWithNanoInvalidValue();
    }

    static void testWithNanoSameValue() {
        LocalTime time = LocalTime.of(12, 30, 45, 500_000_000); // 12:30:45.500
        LocalTime result = time.withNano(500_000_000);
        assert result == time : "withNano should return same instance if nano is unchanged";
    }

    static void testWithNanoDifferentValue() {
        LocalTime time = LocalTime.of(12, 30, 45, 0);
        LocalTime result = time.withNano(999_999_999);
        assert result.getHour() == 12 : "Hour should remain unchanged";
        assert result.getMinute() == 30 : "Minute should remain unchanged";
        assert result.getSecond() == 45 : "Second should remain unchanged";
        assert result.getNano() == 999_999_999 : "Nano should be updated";
        assert result != time : "Result should be a new instance";
    }

    static void testWithNanoInvalidValue() {
        LocalTime time = LocalTime.of(10, 20, 30, 0);
        boolean exceptionThrown = false;
        try {
            time.withNano(-1);
        } catch (DateTimeException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "withNano should throw DateTimeException for negative nano";
        exceptionThrown = false;
        try {
            time.withNano(1_000_000_000);
        } catch (DateTimeException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "withNano should throw DateTimeException for nano >= 1_000_000_000";
    }
}
