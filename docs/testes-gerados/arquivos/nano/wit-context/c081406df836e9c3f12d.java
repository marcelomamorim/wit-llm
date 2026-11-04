/*
 * @test
 * @summary Test for ZonedDateTime.ofInstant method, covering null inputs and normal behavior.
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testOfInstant_NullInstant();
        testOfInstant_NullZone();
        testOfInstant_ValidInputs();
    }

    static void testOfInstant_NullInstant() {
        try {
            ZonedDateTime.ofInstant(null, ZoneId.systemDefault());
            System.err.println("Failed: Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testOfInstant_NullZone() {
        try {
            Instant now = Instant.now();
            ZonedDateTime.ofInstant(now, null);
            System.err.println("Failed: Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testOfInstant_ValidInputs() {
        Instant instant = Instant.ofEpochSecond(123456789L, 987654321L);
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zone);
        if (zdt == null) {
            System.err.println("Failed: ZonedDateTime.ofInstant returned null for valid inputs");
        } else {
            System.out.println("Passed: Valid inputs produce ZonedDateTime: " + zdt);
        }
    }
}
