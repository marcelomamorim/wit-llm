/*
 * @test
 * @summary Verifies HijrahEra.of(int) returns AH for 1 and throws DateTimeException with message "Invalid era: N" for other values
 * @run main HijrahEraWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.HijrahEra;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        checkValid();
        int[] invalid = {0, 2, -1, 3, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int value : invalid) {
            checkInvalid(value);
        }
    }

    static void checkValid() {
        HijrahEra era = HijrahEra.of(1);
        if (era != HijrahEra.AH) {
            throw new AssertionError("of(1) expected AH but was " + era);
        }
    }

    static void checkInvalid(int value) {
        try {
            HijrahEra era = HijrahEra.of(value);
            throw new AssertionError("No exception for value " + value + ", returned " + era);
        } catch (DateTimeException e) {
            String expected = "Invalid era: " + value;
            if (!expected.equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for " + value + ": " + e.getMessage());
            }
        }
    }
}
