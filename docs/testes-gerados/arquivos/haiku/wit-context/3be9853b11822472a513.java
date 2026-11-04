/*
 * @test
 * @summary Witup-derived checks for LocalDate.withYear(int): same-year identity, leap day adjustment, and year range validation
 * @run main LocalDateWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        LocalDate d = LocalDate.of(2000, 1, 15);
        if (d.withYear(2000) != d) {
            throw new RuntimeException("withYear with same year must return this");
        }
        if (d.withYear(2001).getYear() != 2001) {
            throw new RuntimeException("withYear must set the new year");
        }
        if (d.withYear(2001).getDayOfMonth() != 15) {
            throw new RuntimeException("withYear must keep the day of month");
        }
        LocalDate leap = LocalDate.of(2000, 2, 29);
        if (!leap.withYear(2001).equals(LocalDate.of(2001, 2, 28))) {
            throw new RuntimeException("Feb 29 must resolve to Feb 28 in a non-leap year");
        }
        if (d.withYear(999999999).getYear() != 999999999) {
            throw new RuntimeException("maximum valid year must be accepted");
        }
        if (d.withYear(-999999999).getYear() != -999999999) {
            throw new RuntimeException("minimum valid year must be accepted");
        }
        expectDateTimeException(d, 1000000000);
        expectDateTimeException(d, -1000000000);
    }

    static void expectDateTimeException(LocalDate d, int year) {
        try {
            d.withYear(year);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for invalid year " + year);
    }
}
