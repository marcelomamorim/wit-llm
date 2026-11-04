/*
 * @test
 * @summary Test for ChronoZonedDateTimeImpl withZoneSameInstant method
 * @run main ChronoZonedDateTimeImplWitupTest
 */

import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.time.ZoneOffset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

public class ChronoZonedDateTimeImplWitupTest {

    public static void main(String[] args) {
        testWithZoneSameInstant_NullZone();
        testWithZoneSameInstant_SameZone();
        testWithZoneSameInstant_DifferentZone();
    }

    public static void testWithZoneSameInstant_NullZone() {
        ChronoZonedDateTime<?> dateTime = createSampleZonedDateTime();
        try {
            dateTime.withZoneSameInstant(null);
            System.err.println("Failed: Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testWithZoneSameInstant_SameZone() {
        ChronoZonedDateTime<?> dateTime = createSampleZonedDateTime();
        ZoneId zone = dateTime.getZone();
        ChronoZonedDateTime<?> result = dateTime.withZoneSameInstant(zone);
        if (result != dateTime) {
            System.err.println("Failed: withZoneSameInstant with same zone should return same instance");
        }
    }

    public static void testWithZoneSameInstant_DifferentZone() {
        ChronoZonedDateTime<?> dateTime = createSampleZonedDateTime();
        ZoneId newZone = ZoneId.of("America/New_York");
        if (Objects.equals(dateTime.getZone(), newZone)) {
            newZone = ZoneId.of("Asia/Tokyo");
        }
        ChronoZonedDateTime<?> result = dateTime.withZoneSameInstant(newZone);
        if (result == dateTime) {
            System.err.println("Failed: withZoneSameInstant with different zone should return new instance");
        }
        if (!Objects.equals(result.getZone(), newZone)) {
            System.err.println("Failed: Result zone does not match expected new zone");
        }
        // Verify that the instant remains the same
        if (!result.toInstant().equals(dateTime.toInstant())) {
            System.err.println("Failed: Instant should remain the same after withZoneSameInstant");
        }
    }

    private static ChronoZonedDateTime<?> createSampleZonedDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2023, 3, 15, 12, 0);
        ZoneId zone = ZoneOffset.UTC;
        Instant instant = ldt.toInstant(zone.getRules().getOffset(ldt));
        return new ChronoZonedDateTime<>(instant, zone);
    }
}
