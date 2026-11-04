/*
 * @test
 * @summary Behavioral tests for Instant.ofEpochSecond(long) using only public API
 * @run main InstantWitupTest
 */

import java.time.DateTimeException;
import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testEpoch();
        testPositiveSecond();
        testNegativeSecond();
        testBoundsRoundTrip();
        testEquivalentToTwoArgFactory();
        testOutOfRangeThrows();
    }

    static void testEpoch() {
        Instant i = Instant.ofEpochSecond(0L);
        check(i.equals(Instant.EPOCH), "epoch equals EPOCH");
        check(i.getEpochSecond() == 0L, "epoch seconds 0");
        check(i.getNano() == 0, "epoch nano 0");
    }

    static void testPositiveSecond() {
        Instant i = Instant.ofEpochSecond(1234567890L);
        check(i.getEpochSecond() == 1234567890L, "positive seconds preserved");
        check(i.getNano() == 0, "positive nano is zero");
        check(i.toEpochMilli() == 1234567890000L, "epoch millis derived correctly");
    }

    static void testNegativeSecond() {
        Instant i = Instant.ofEpochSecond(-1L);
        check(i.getEpochSecond() == -1L, "negative seconds preserved");
        check(i.getNano() == 0, "negative nano is zero");
        check(i.toEpochMilli() == -1000L, "negative epoch millis derived correctly");
    }

    static void testBoundsRoundTrip() {
        long min = Instant.MIN.getEpochSecond();
        long max = Instant.MAX.getEpochSecond();
        check(Instant.ofEpochSecond(min).equals(Instant.MIN), "MIN bound round trip");
        check(Instant.ofEpochSecond(max).equals(Instant.MAX), "MAX bound round trip");
    }

    static void testEquivalentToTwoArgFactory() {
        long[] samples = {0L, 1L, -1L, 86400L, -86400L, 1000000000L};
        for (long s : samples) {
            check(Instant.ofEpochSecond(s).equals(Instant.ofEpochSecond(s, 0L)),
                  "ofEpochSecond(s) equals ofEpochSecond(s, 0) for " + s);
        }
    }

    static void testOutOfRangeThrows() {
        check(throwsDateTime(Long.MAX_VALUE), "Long.MAX_VALUE rejected");
        check(throwsDateTime(Long.MIN_VALUE), "Long.MIN_VALUE rejected");
    }

    static boolean throwsDateTime(long s) {
        try {
            Instant.ofEpochSecond(s);
            return false;
        } catch (DateTimeException e) {
            return true;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
