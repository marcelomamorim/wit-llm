/*
 * @test
 * @summary Verifies HijrahDate.toEpochDay consistency with LocalDate epoch days, day increments and round trips
 * @run main HijrahDateWitupTest
 */
import java.time.LocalDate;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        long e1 = HijrahDate.of(1433, 1, 1).toEpochDay();
        long e2 = HijrahDate.of(1433, 1, 2).toEpochDay();
        if (e2 - e1 != 1) {
            throw new RuntimeException("Consecutive days must differ by 1: " + e1 + " " + e2);
        }

        long eDec = HijrahDate.of(1433, 12, 1).toEpochDay();
        long delta = eDec - e1;
        if (delta < 300 || delta > 360) {
            throw new RuntimeException("Unexpected epoch day span within year: " + delta);
        }

        LocalDate base = LocalDate.of(2011, 11, 26);
        if (HijrahDate.from(base).toEpochDay() != base.toEpochDay()) {
            throw new RuntimeException("toEpochDay mismatch for " + base);
        }

        for (int i = 0; i < 10; i++) {
            LocalDate ld = base.plusDays(i * 17L);
            long expected = ld.toEpochDay();
            long actual = HijrahDate.from(ld).toEpochDay();
            if (actual != expected) {
                throw new RuntimeException("Round trip mismatch for " + ld + ": " + actual + " != " + expected);
            }
        }
    }
}
