/*
 * @test
 * @summary Verifies that LocalDateTime.now(Clock) rejects a null clock with a NullPointerException
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        try {
            LocalDateTime.now(null);
            throw new RuntimeException("Expected NullPointerException for null clock");
        } catch (NullPointerException expected) {
            if (!"clock".equals(expected.getMessage())) {
                throw new RuntimeException("Unexpected NPE message: " + expected.getMessage(), expected);
            }
        }
    }
}
