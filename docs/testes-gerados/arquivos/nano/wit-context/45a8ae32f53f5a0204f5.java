/*
 * @test
 * @summary Teste do método LocalDateTime.now(Clock clock) para verificar comportamento com entrada nula.
 * @run main LocalDateTimeWitupTest
 */

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testNowWithNullClock();
        testNowWithValidClock();
    }

    public static void testNowWithNullClock() {
        try {
            LocalDateTime.now(null);
            System.err.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            String message = e.getMessage();
            if (message != null && message.contains("clock")) {
                System.out.println("PASS: NullPointerException thrown as expected with message: " + message);
            } else {
                System.err.println("FAIL: NullPointerException thrown but message does not contain 'clock'");
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }

    public static void testNowWithValidClock() {
        Clock clock = Clock.systemUTC();
        try {
            LocalDateTime result = LocalDateTime.now(clock);
            if (result != null) {
                System.out.println("PASS: LocalDateTime.now(clock) returned: " + result);
            } else {
                System.err.println("FAIL: LocalDateTime.now(clock) returned null");
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
