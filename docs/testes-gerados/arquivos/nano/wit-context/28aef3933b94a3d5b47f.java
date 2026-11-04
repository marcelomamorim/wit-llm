/*
 * @test
 * @summary Testa o método dateNow com Clock válido e nulo
 * @run main IsoChronologyWitupTest
 */

import java.time.Clock;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testDateNowWithValidClock();
        testDateNowWithNullClock();
    }

    static void testDateNowWithValidClock() {
        Clock clock = Clock.systemUTC();
        try {
            LocalDate result = IsoChronology.INSTANCE.dateNow(clock);
            if (result == null) {
                throw new AssertionError("Expected non-null result with valid clock");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception with valid clock", e);
        }
    }

    static void testDateNowWithNullClock() {
        try {
            IsoChronology.INSTANCE.dateNow(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
