/*
 * @test
 * @summary Witup-generated tests for HijrahEra.of(int)
 * @run main HijrahEraWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.HijrahEra;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        if (HijrahEra.of(1) != HijrahEra.AH) {
            throw new RuntimeException("of(1) should return AH");
        }
        checkInvalid(0);
        checkInvalid(2);
        checkInvalid(-1);
        checkInvalid(Integer.MIN_VALUE);
    }

    private static void checkInvalid(int value) {
        try {
            HijrahEra.of(value);
        } catch (DateTimeException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Invalid era")) {
                throw new RuntimeException("unexpected message for " + value + ": " + msg);
            }
            return;
        }
        throw new RuntimeException("expected DateTimeException for " + value);
    }
}
