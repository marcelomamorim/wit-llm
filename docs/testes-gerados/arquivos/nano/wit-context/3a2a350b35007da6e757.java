/*
 * @test
 * @summary Tests for java.time.LocalTime.ofSecondOfDay(long)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.DateTimeException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidSecondOfDay();
        testNegativeSecondOfDay();
        testExceedingSecondOfDay();
    }

    static void testValidSecondOfDay() {
        long validSecond = 3600L; // 1 hour
        LocalTime time = LocalTime.ofSecondOfDay(validSecond);
        assert time.getHour() == 1 : "Expected hour 1";
        assert time.getMinute() == 0 : "Expected minute 0";
        assert time.getSecond() == 0 : "Expected second 0";
    }

    static void testNegativeSecondOfDay() {
        long negativeSecond = -1L;
        try {
            LocalTime.ofSecondOfDay(negativeSecond);
            assert false : "Expected DateTimeException for negative second";
        } catch (DateTimeException e) {
            // Expected exception
        }
    }

    static void testExceedingSecondOfDay() {
        long exceedingSecond = 86400L; // 24 hours in seconds
        try {
            LocalTime.ofSecondOfDay(exceedingSecond);
            assert false : "Expected DateTimeException for exceeding second";
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
