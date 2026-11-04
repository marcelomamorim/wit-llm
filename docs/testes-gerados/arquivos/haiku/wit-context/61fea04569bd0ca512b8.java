/*
 * @test
 * @summary Witup-derived checks for Instant.plusSeconds public behavior and exceptions
 * @run main InstantWitupTest
 */
import java.time.DateTimeException;
import java.time.Instant;

public class InstantWitupTest {
    public static void main(String[] args) {
        check(Instant.ofEpochSecond(10).plusSeconds(0).equals(Instant.ofEpochSecond(10)), "zero seconds");
        check(Instant.ofEpochSecond(10).plusSeconds(5).equals(Instant.ofEpochSecond(15)), "positive seconds");
        check(Instant.ofEpochSecond(10).plusSeconds(-3).equals(Instant.ofEpochSecond(7)), "negative seconds");
        check(Instant.ofEpochSecond(5, 500).plusSeconds(2).equals(Instant.ofEpochSecond(7, 500)), "nanos preserved");
        check(Instant.ofEpochSecond(-1, 999999999).plusSeconds(1).equals(Instant.ofEpochSecond(0, 999999999)), "negative epoch with nanos");

        boolean thrown = false;
        try {
            Instant.MAX.plusSeconds(1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "MAX.plusSeconds(1) must throw DateTimeException");

        thrown = false;
        try {
            Instant.MIN.plusSeconds(-1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "MIN.plusSeconds(-1) must throw DateTimeException");

        thrown = false;
        try {
            Instant.MAX.plusSeconds(Long.MAX_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "MAX.plusSeconds(Long.MAX_VALUE) must throw ArithmeticException");

        thrown = false;
        try {
            Instant.MIN.plusSeconds(Long.MIN_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "MIN.plusSeconds(Long.MIN_VALUE) must throw ArithmeticException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
