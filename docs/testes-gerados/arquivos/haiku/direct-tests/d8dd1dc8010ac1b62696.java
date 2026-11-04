/*
 * @test
 * @summary Verifies Duration.plusMillis normalization and equivalence with plus(TemporalAmount)
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        Duration a = Duration.ZERO.plusMillis(1500);
        check(a.getSeconds() == 1, "1500ms seconds");
        check(a.getNano() == 500_000_000, "1500ms nanos");

        Duration b = Duration.ofSeconds(1).plusMillis(-1);
        check(b.getSeconds() == 0, "1s-1ms seconds");
        check(b.getNano() == 999_000_000, "1s-1ms nanos");

        Duration c = Duration.ZERO.plusMillis(-1500);
        check(c.getSeconds() == -2, "-1500ms seconds");
        check(c.getNano() == 500_000_000, "-1500ms nanos");

        check(Duration.ZERO.plusMillis(0).isZero(), "plusMillis(0) is zero");

        check(Duration.ofSeconds(5).plusMillis(2000).equals(Duration.ofSeconds(7)),
              "5s + 2000ms == 7s");

        check(Duration.ofSeconds(10).plusMillis(-10000).isZero(),
              "10s - 10000ms is zero");

        check(Duration.ZERO.plusMillis(1).toNanos() == 1_000_000L,
              "1ms in nanos");

        Duration base = Duration.ofSeconds(3, 250_000_000);
        check(base.plusMillis(750).equals(base.plus(Duration.ofMillis(750))),
              "plusMillis equals plus(ofMillis)");
        check(base.plusMillis(-3250).isZero(),
              "3.25s - 3250ms is zero");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
