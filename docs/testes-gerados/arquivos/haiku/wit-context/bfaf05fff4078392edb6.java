/*
 * @test
 * @summary Verifies that LocalTime.now(Clock) rejects a null clock with NullPointerException
 * @run main LocalTimeWitupTest
 */

import java.time.Clock;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        boolean thrown = false;
        try {
            LocalTime.now((Clock) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null clock");
        }
    }
}
