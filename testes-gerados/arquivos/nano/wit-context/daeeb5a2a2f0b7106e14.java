/*
 * @test
 * @summary Testa o método YearMonth.now(Clock) para verificar comportamento padrão e exceções.
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    static void testNowWithValidClock() {
        Clock clock = Clock.fixed(Instant.parse("2023-03-15T00:00:00Z"), ZoneId.of("UTC"));
        YearMonth ym = YearMonth.now(clock);
        if (ym.getYear() != 2023 || ym.getMonthValue() != 3) {
            throw new AssertionError("Expected YearMonth for March 2023, but got: " + ym);
        }
    }

    static void testNowWithNullClock() {
        try {
            YearMonth.now(null);
            throw new AssertionError("Expected NullPointerException when clock is null");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
