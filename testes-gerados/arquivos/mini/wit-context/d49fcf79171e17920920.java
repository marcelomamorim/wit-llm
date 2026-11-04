/*
 * @test
 * @summary Test LocalDateTime.ofInstant(Instant, ZoneId) for null checks and basic correctness
 * @run main LocalDateTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testValidInstantAndZone();
    }

    private static void testNullInstant() {
        try {
            LocalDateTime.ofInstant(null, ZoneId.systemDefault());
            throw new RuntimeException("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testNullZone() {
        Instant instant = Instant.now();
        try {
            LocalDateTime.ofInstant(instant, null);
            throw new RuntimeException("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testValidInstantAndZone() {
        Instant instant = Instant.ofEpochSecond(0L, 123456789);
        ZoneId zone = ZoneOffset.UTC;
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, zone);
        if (ldt.getYear() != 1970 || ldt.getMonthValue() != 1 || ldt.getDayOfMonth() != 1) {
            throw new RuntimeException("Unexpected date part for epoch instant");
        }
        if (ldt.getHour() != 0 || ldt.getMinute() != 0 || ldt.getSecond() != 0) {
            throw new RuntimeException("Unexpected time part for epoch instant");
        }
        if (ldt.getNano() != 123456789) {
            throw new RuntimeException("Unexpected nano part for epoch instant");
        }
    }
}
