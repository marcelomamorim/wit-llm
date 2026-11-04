/*
 * @test
 * @summary Test java.time.LocalDateTime.ofInstant(Instant, ZoneId) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testOfInstantBasic();
    }

    private static void testNullInstant() {
        try {
            LocalDateTime.ofInstant(null, ZoneOffset.UTC);
            throw new RuntimeException("Expected NullPointerException for null instant");
        } catch (NullPointerException npe) {
            if (!"instant".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch for null instant");
            }
        }
    }

    private static void testNullZone() {
        try {
            LocalDateTime.ofInstant(Instant.EPOCH, null);
            throw new RuntimeException("Expected NullPointerException for null zone");
        } catch (NullPointerException npe) {
            if (!"zone".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch for null zone");
            }
        }
    }

    private static void testOfInstantBasic() {
        Instant instant = Instant.ofEpochSecond(0L, 123456789);
        ZoneId zone = ZoneOffset.UTC;
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, zone);
        // The result should be 1970-01-01T00:00:00.123456789 at UTC
        if (ldt.getYear() != 1970 || ldt.getMonthValue() != 1 || ldt.getDayOfMonth() != 1) {
            throw new RuntimeException("Date part mismatch");
        }
        if (ldt.getHour() != 0 || ldt.getMinute() != 0 || ldt.getSecond() != 0) {
            throw new RuntimeException("Time part mismatch");
        }
        if (ldt.getNano() != 123456789) {
            throw new RuntimeException("Nano part mismatch");
        }

        // Test with a non-UTC offset zone
        ZoneId zonePlus2 = ZoneOffset.ofHours(2);
        LocalDateTime ldt2 = LocalDateTime.ofInstant(instant, zonePlus2);
        // The local time should be instant + 2 hours
        if (ldt2.getHour() != 2) {
            throw new RuntimeException("Hour mismatch for offset +2");
        }
    }
}
