/*
 * @test
 * @summary Test java.time.LocalTime.withNano(int) method for valid and invalid inputs
 * @run main java.time.LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testWithNanoValid();
        testWithNanoSameValue();
        testWithNanoInvalid();
        System.out.println("All tests passed.");
    }

    private static void testWithNanoValid() {
        LocalTime base = LocalTime.of(10, 15, 30, 123456789);
        LocalTime changed = base.withNano(987654321);
        if (changed.getNano() != 987654321) {
            throw new AssertionError("withNano did not set nano correctly");
        }
        if (changed.getHour() != 10 || changed.getMinute() != 15 || changed.getSecond() != 30) {
            throw new AssertionError("withNano changed other fields unexpectedly");
        }
    }

    private static void testWithNanoSameValue() {
        LocalTime base = LocalTime.of(5, 0, 0, 500);
        LocalTime result = base.withNano(500);
        if (result != base) {
            throw new AssertionError("withNano should return same instance if nano is unchanged");
        }
    }

    private static void testWithNanoInvalid() {
        LocalTime base = LocalTime.of(0, 0);
        int[] invalidNanos = {-1, 1000000000};
        for (int invalidNano : invalidNanos) {
            boolean thrown = false;
            try {
                base.withNano(invalidNano);
            } catch (DateTimeException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("withNano did not throw DateTimeException for invalid nano: " + invalidNano);
            }
        }
    }
}
