/*
 * @test
 * @summary Verify HijrahDate.now(ZoneId) returns the current Hijrah date for the given zone
 * @run main HijrahDateWitupTest
 */

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        ZoneId[] zones = {
            ZoneId.of("UTC"),
            ZoneId.of("Asia/Tokyo"),
            ZoneId.of("America/Los_Angeles")
        };
        for (ZoneId zone : zones) {
            checkNow(zone);
        }
    }

    static void checkNow(ZoneId zone) {
        LocalDate before = LocalDate.now(zone);
        HijrahDate result = HijrahDate.now(zone);
        LocalDate after = LocalDate.now(zone);

        if (result == null) {
            throw new AssertionError("now(" + zone + ") returned null");
        }
        if (!result.getChronology().equals(HijrahChronology.INSTANCE)) {
            throw new AssertionError("Unexpected chronology for " + zone);
        }

        HijrahDate expectedBefore = HijrahDate.from(before);
        HijrahDate expectedAfter = HijrahDate.from(after);
        if (!result.equals(expectedBefore) && !result.equals(expectedAfter)) {
            throw new AssertionError("now(" + zone + ") = " + result
                    + ", expected " + expectedBefore + " or " + expectedAfter);
        }
    }
}
