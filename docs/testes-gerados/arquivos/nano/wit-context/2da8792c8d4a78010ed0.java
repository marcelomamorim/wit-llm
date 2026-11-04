/*
 * @test
 * @summary Tests for java.time.LocalTime.withSecond(int)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testWithSecondValid();
        testWithSecondNoChange();
        testWithSecondInvalidBelow();
        testWithSecondInvalidAbove();
    }

    static void testWithSecondValid() {
        LocalTime time = LocalTime.of(10, 30, 45);
        int newSecond = 15;
        LocalTime result = time.withSecond(newSecond);
        if (result == time) {
            throw new AssertionError("Expected new instance, but got same instance");
        }
        assert result.getSecond() == newSecond : "Second not updated correctly";
        assert result.getHour() == 10 : "Hour changed unexpectedly";
        assert result.getMinute() == 30 : "Minute changed unexpectedly";
    }

    static void testWithSecondNoChange() {
        LocalTime time = LocalTime.of(10, 30, 45);
        LocalTime result = time.withSecond(45);
        if (result != time) {
            throw new AssertionError("Expected same instance when second is unchanged");
        }
    }

    static void testWithSecondInvalidBelow() {
        try {
            LocalTime.of(10, 30, 45).withSecond(-1);
            throw new AssertionError("Expected DateTimeException for second below 0");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testWithSecondInvalidAbove() {
        try {
            LocalTime.of(10, 30, 45).withSecond(60);
            throw new AssertionError("Expected DateTimeException for second above 59");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
