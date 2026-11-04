/*
 * @test
 * @summary Verifies LocalDate.toEpochSecond null checks and basic epoch second results
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        LocalDate epoch = LocalDate.of(1970, 1, 1);
        LocalTime midnight = LocalTime.of(0, 0);
        LocalTime oneAm = LocalTime.of(1, 0);

        check(epoch.toEpochSecond(midnight, ZoneOffset.UTC) == 0L, "epoch midnight UTC");
        check(epoch.toEpochSecond(oneAm, ZoneOffset.UTC) == 3600L, "epoch one hour UTC");
        check(LocalDate.of(1970, 1, 2).toEpochSecond(midnight, ZoneOffset.UTC) == 86400L, "next day midnight");
        check(LocalDate.of(1969, 12, 31).toEpochSecond(midnight, ZoneOffset.UTC) == -86400L, "previous day midnight");

        boolean npeNullTime = false;
        try {
            epoch.toEpochSecond(null, ZoneOffset.UTC);
        } catch (NullPointerException e) {
            npeNullTime = true;
        }
        check(npeNullTime, "null time must throw NullPointerException");

        boolean npeNullOffset = false;
        try {
            epoch.toEpochSecond(midnight, null);
        } catch (NullPointerException e) {
            npeNullOffset = true;
        }
        check(npeNullOffset, "null offset must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
