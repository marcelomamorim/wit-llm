/*
 * @test
 * @summary Verify Month.of(int) returns constants for valid values and throws DateTimeException for out-of-range values
 * @run main MonthWitupTest
 */

import java.time.DateTimeException;
import java.time.Month;

public class MonthWitupTest {

    public static void main(String[] args) {
        for (int i = 1; i <= 12; i++) {
            Month m = Month.of(i);
            if (m.getValue() != i) {
                throw new RuntimeException("Expected value " + i + " but got " + m.getValue());
            }
        }
        if (Month.of(1) != Month.JANUARY) {
            throw new RuntimeException("Month.of(1) should be JANUARY");
        }
        if (Month.of(12) != Month.DECEMBER) {
            throw new RuntimeException("Month.of(12) should be DECEMBER");
        }
        checkInvalid(0);
        checkInvalid(13);
        checkInvalid(-1);
        checkInvalid(Integer.MIN_VALUE);
        checkInvalid(Integer.MAX_VALUE);
    }

    private static void checkInvalid(int value) {
        try {
            Month.of(value);
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException for " + value);
    }
}
