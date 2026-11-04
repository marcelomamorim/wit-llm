/*
 * @test
 * @summary IsoChronology.dateNow must reject a null clock and return the date provided by the clock
 * @run main IsoChronologyWitupTest
 */
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        testNullClock();
        testFixedClock();
    }

    static void testNullClock() {
        try {
            IsoChronology.INSTANCE.dateNow(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null clock");
    }

    static void testFixedClock() {
        Clock clock = Clock.fixed(Instant.parse("2018-09-15T10:00:00Z"), ZoneOffset.UTC);
        LocalDate date = IsoChronology.INSTANCE.dateNow(clock);
        LocalDate expected = LocalDate.of(2018, 9, 15);
        if (!expected.equals(date)) {
            throw new RuntimeException("unexpected date: " + date);
        }
    }
}
