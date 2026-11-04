/*
 * @test
 * @summary LocalDateTime.minusNanos basic and boundary behavior
 * @run main LocalDateTimeWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2020, 1, 1, 0, 0);
        check(base.minusNanos(1L).equals(LocalDateTime.of(2019, 12, 31, 23, 59, 59, 999_999_999)), "borrow across midnight");
        check(base.minusNanos(0L).equals(base), "zero nanos");
        check(base.minusNanos(-1L).equals(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 1)), "negative nanos");
        LocalDateTime d = LocalDateTime.of(2020, 3, 1, 12, 0);
        check(d.minusNanos(86_400L * 1_000_000_000L).equals(LocalDateTime.of(2020, 2, 29, 12, 0)), "one day in leap year");
        LocalDateTime min = LocalDateTime.of(-999999999, 1, 1, 0, 0);
        boolean thrown = false;
        try {
            min.minusNanos(1L);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "underflow beyond minimum year must throw DateTimeException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
