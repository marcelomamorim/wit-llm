/*
 * @test
 * @summary Verifies IsoChronology.dateYearDay delegates to LocalDate.ofYearDay for valid and invalid inputs
 * @run main IsoChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        IsoChronology iso = IsoChronology.INSTANCE;

        check(iso.dateYearDay(2020, 60), LocalDate.of(2020, 2, 29));
        check(iso.dateYearDay(2019, 1), LocalDate.of(2019, 1, 1));
        check(iso.dateYearDay(2019, 365), LocalDate.of(2019, 12, 31));
        check(iso.dateYearDay(2020, 366), LocalDate.of(2020, 12, 31));
        check(iso.dateYearDay(2000, 60), LocalDate.of(2000, 2, 29));
        check(iso.dateYearDay(1900, 60), LocalDate.of(1900, 3, 1));
        check(iso.dateYearDay(-1, 1), LocalDate.of(-1, 1, 1));

        expectDateTimeException(iso, 2019, 366);
        expectDateTimeException(iso, 2019, 0);
        expectDateTimeException(iso, 2019, 367);

        if (failures > 0) {
            throw new RuntimeException("IsoChronology.dateYearDay failures: " + failures);
        }
    }

    private static void check(LocalDate actual, LocalDate expected) {
        if (!expected.equals(actual)) {
            failures++;
            System.err.println("Expected " + expected + " but got " + actual);
        }
    }

    private static void expectDateTimeException(IsoChronology iso, int year, int dayOfYear) {
        try {
            LocalDate result = iso.dateYearDay(year, dayOfYear);
            failures++;
            System.err.println("Expected DateTimeException for year " + year + " day " + dayOfYear + " but got " + result);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
