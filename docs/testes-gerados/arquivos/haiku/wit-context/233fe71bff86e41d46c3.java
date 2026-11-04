/*
 * @test
 * @summary Witup-generated checks for IsoChronology.dateEpochDay boundaries and DateTimeException
 * @run main IsoChronologyWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        IsoChronology iso = IsoChronology.INSTANCE;

        if (!iso.dateEpochDay(0L).equals(LocalDate.of(1970, 1, 1))) {
            throw new RuntimeException("epoch day 0 must map to 1970-01-01");
        }
        if (!iso.dateEpochDay(LocalDate.MAX.toEpochDay()).equals(LocalDate.MAX)) {
            throw new RuntimeException("LocalDate.MAX epoch day must round-trip");
        }
        if (!iso.dateEpochDay(LocalDate.MIN.toEpochDay()).equals(LocalDate.MIN)) {
            throw new RuntimeException("LocalDate.MIN epoch day must round-trip");
        }

        expectDateTimeException(iso, LocalDate.MAX.toEpochDay() + 1L);
        expectDateTimeException(iso, LocalDate.MIN.toEpochDay() - 1L);
        expectDateTimeException(iso, Long.MAX_VALUE);
        expectDateTimeException(iso, Long.MIN_VALUE);
    }

    static void expectDateTimeException(IsoChronology iso, long epochDay) {
        try {
            iso.dateEpochDay(epochDay);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for epochDay " + epochDay);
    }
}
