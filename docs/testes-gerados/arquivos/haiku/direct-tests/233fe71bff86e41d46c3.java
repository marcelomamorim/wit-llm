/*
 * @test
 * @summary Verifies IsoChronology.dateEpochDay maps epoch days to the expected ISO LocalDate values and rejects out-of-range input
 * @run main IsoChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        IsoChronology iso = IsoChronology.INSTANCE;

        check(LocalDate.of(1970, 1, 1).equals(iso.dateEpochDay(0L)), "epoch day 0");
        check(LocalDate.of(1969, 12, 31).equals(iso.dateEpochDay(-1L)), "epoch day -1");
        check(LocalDate.of(1970, 1, 2).equals(iso.dateEpochDay(1L)), "epoch day 1");

        LocalDate leap = LocalDate.of(2000, 2, 29);
        check(leap.equals(iso.dateEpochDay(leap.toEpochDay())), "leap day round trip");

        check(LocalDate.MIN.equals(iso.dateEpochDay(LocalDate.MIN.toEpochDay())), "LocalDate.MIN round trip");
        check(LocalDate.MAX.equals(iso.dateEpochDay(LocalDate.MAX.toEpochDay())), "LocalDate.MAX round trip");

        for (long d = -1000L; d <= 1000L; d++) {
            LocalDate expected = LocalDate.ofEpochDay(d);
            LocalDate actual = iso.dateEpochDay(d);
            check(expected.equals(actual), "mismatch at epoch day " + d);
            check(actual.toEpochDay() == d, "toEpochDay mismatch at " + d);
        }

        boolean thrown = false;
        try {
            iso.dateEpochDay(LocalDate.MAX.toEpochDay() + 1L);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "out-of-range epoch day should throw DateTimeException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
