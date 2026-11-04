/*
 * @test
 * @summary Test LocalDateTime.plusMinutes(long) for various inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import static java.time.LocalDateTime.of;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPlusMinutesZero();
        testPlusMinutesPositive();
        testPlusMinutesNegative();
        testPlusMinutesLarge();
    }

    static void testPlusMinutesZero() {
        LocalDateTime dt = of(2023, 3, 15, 12, 0);
        LocalDateTime result = dt.plusMinutes(0);
        assert result.equals(dt) : "plusMinutes(0) should return the same date-time";
    }

    static void testPlusMinutesPositive() {
        LocalDateTime dt = of(2023, 3, 15, 12, 0);
        LocalDateTime result = dt.plusMinutes(30);
        LocalDateTime expected = of(2023, 3, 15, 12, 30);
        assert result.equals(expected) : "plusMinutes(30) should add 30 minutes";
    }

    static void testPlusMinutesNegative() {
        LocalDateTime dt = of(2023, 3, 15, 12, 0);
        LocalDateTime result = dt.plusMinutes(-45);
        LocalDateTime expected = of(2023, 3, 15, 11, 15);
        assert result.equals(expected) : "plusMinutes(-45) should subtract 45 minutes";
    }

    static void testPlusMinutesLarge() {
        LocalDateTime dt = of(2023, 3, 15, 12, 0);
        LocalDateTime result = dt.plusMinutes(1440); // 1 day
        LocalDateTime expected = of(2023, 3, 16, 12, 0);
        assert result.equals(expected) : "plusMinutes(1440) should add 1440 minutes (1 day)";
    }
}
