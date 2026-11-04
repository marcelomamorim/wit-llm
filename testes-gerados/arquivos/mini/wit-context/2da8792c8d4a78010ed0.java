/*
 * @test
 * @summary Test java.time.LocalTime.withSecond(int) method for valid and invalid inputs
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testWithSecondValid();
        testWithSecondSameValueReturnsThis();
        testWithSecondInvalidLow();
        testWithSecondInvalidHigh();
        System.out.println("All tests passed.");
    }

    private static void testWithSecondValid() {
        LocalTime time = LocalTime.of(10, 15, 30, 123);
        LocalTime changed = time.withSecond(45);
        if (changed.getSecond() != 45) {
            throw new AssertionError("Expected second to be 45 but was " + changed.getSecond());
        }
        if (changed.getHour() != 10 || changed.getMinute() != 15 || changed.getNano() != 123) {
            throw new AssertionError("Other fields changed unexpectedly");
        }
        if (changed == time) {
            throw new AssertionError("Expected a new instance when second is different");
        }
    }

    private static void testWithSecondSameValueReturnsThis() {
        LocalTime time = LocalTime.of(5, 20, 10);
        LocalTime result = time.withSecond(10);
        if (result != time) {
            throw new AssertionError("Expected same instance when second is unchanged");
        }
    }

    private static void testWithSecondInvalidLow() {
        LocalTime time = LocalTime.of(0, 0, 0);
        try {
            time.withSecond(-1);
            throw new AssertionError("Expected DateTimeException for second -1");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testWithSecondInvalidHigh() {
        LocalTime time = LocalTime.of(0, 0, 0);
        try {
            time.withSecond(60);
            throw new AssertionError("Expected DateTimeException for second 60");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
