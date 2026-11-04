/*
 * @test
 * @summary Witup-generated behavioural checks for Duration.minusNanos
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        int failures = 0;

        Duration negOne = Duration.ZERO.minusNanos(1);
        if (negOne.getSeconds() != -1L || negOne.getNano() != 999_999_999) {
            System.err.println("FAIL: ZERO.minusNanos(1) expected seconds=-1 nano=999999999 but was " + negOne);
            failures++;
        }

        if (!Duration.ofNanos(5).minusNanos(3).equals(Duration.ofNanos(2))) {
            System.err.println("FAIL: ofNanos(5).minusNanos(3) should equal ofNanos(2)");
            failures++;
        }

        if (!Duration.ofSeconds(10).minusNanos(0).equals(Duration.ofSeconds(10))) {
            System.err.println("FAIL: ofSeconds(10).minusNanos(0) should be unchanged");
            failures++;
        }

        Duration minLong = Duration.ZERO.minusNanos(Long.MIN_VALUE);
        if (!minLong.equals(Duration.ofSeconds(9_223_372_036L, 854_775_808L))) {
            System.err.println("FAIL: ZERO.minusNanos(Long.MIN_VALUE) produced " + minLong);
            failures++;
        }

        boolean thrown = false;
        try {
            Duration.ofSeconds(Long.MAX_VALUE, 999_999_999).minusNanos(-1);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            System.err.println("FAIL: overflow on minusNanos(-1) at Long.MAX_VALUE seconds should throw ArithmeticException");
            failures++;
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
