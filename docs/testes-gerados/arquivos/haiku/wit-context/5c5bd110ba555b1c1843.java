/*
 * @test
 * @summary Witup-generated test for HijrahDate.now(ZoneId): result date must match the system date in the given zone
 * @run main HijrahDateWitupTest
 */

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        checkZone(ZoneId.of("UTC"));
        checkZone(ZoneId.of("Asia/Tokyo"));
        checkZone(ZoneId.of("America/Los_Angeles"));
    }

    private static void checkZone(ZoneId zone) {
        LocalDate before = LocalDate.now(zone);
        HijrahDate hijrah = HijrahDate.now(zone);
        LocalDate after = LocalDate.now(zone);

        if (hijrah == null) {
            throw new RuntimeException("now(" + zone + ") returned null");
        }

        long epochDay = hijrah.toEpochDay();
        if (epochDay < before.toEpochDay() || epochDay > after.toEpochDay()) {
            throw new RuntimeException("now(" + zone + ") epochDay " + epochDay
                    + " not in [" + before.toEpochDay() + ", " + after.toEpochDay() + "]");
        }
    }
}
