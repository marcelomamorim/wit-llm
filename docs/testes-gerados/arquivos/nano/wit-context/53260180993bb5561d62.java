/*
 * @test
 * @summary Teste do método static ZonedDateTime.now(Clock)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    static void testNowWithValidClock() {
        Clock clock = Clock.systemUTC();
        ZonedDateTime zdt = ZonedDateTime.now(clock);
        if (zdt == null) {
            throw new AssertionError("ZonedDateTime.now(Clock) retornou null com clock válido");
        }
        System.out.println("testNowWithValidClock: PASSED");
    }

    static void testNowWithNullClock() {
        try {
            ZonedDateTime.now(null);
            throw new AssertionError("Esperado NullPointerException ao passar null como Clock");
        } catch (NullPointerException e) {
            if (!"clock".equals(e.getMessage())) {
                throw new AssertionError("Mensagem de exceção inesperada: " + e.getMessage());
            }
        }
        System.out.println("testNowWithNullClock: PASSED");
    }
}
