/*
 * @test
 * @summary Witup-derived checks for Instant.minusNanos behaviour and range errors
 * @run main InstantWitupTest
 */

import java.time.DateTimeException;
import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testRegularSubtraction();
        testZeroAndNegativeArguments();
        testBorderAroundEpoch();
        testLongMinValueArgument();
        testOutOfRangeResults();
    }

    static void testRegularSubtraction() {
        check(Instant.EPOCH.minusNanos(0L).equals(Instant.EPOCH), "zero subtraction");
        check(Instant.EPOCH.minusNanos(1L).equals(Instant.ofEpochSecond(-1L, 999999999L)), "EPOCH minus 1ns");
        check(Instant.ofEpochSecond(0L, 500L).minusNanos(1000L)
                .equals(Instant.ofEpochSecond(-1L, 999999500L)), "borrow across second");
        check(Instant.ofEpochSecond(5L).minusNanos(1000000000L)
                .equals(Instant.ofEpochSecond(4L)), "subtract one second in nanos");
    }

    static void testZeroAndNegativeArguments() {
        check(Instant.ofEpochSecond(0L, 0L).minusNanos(-1L)
                .equals(Instant.ofEpochSecond(0L, 1L)), "negative nanos adds");
        check(Instant.ofEpochSecond(10L, 7L).minusNanos(-3L)
                .equals(Instant.ofEpochSecond(10L, 10L)), "negative nanos adds with nano field");
    }

    static void testBorderAroundEpoch() {
        Instant one = Instant.ofEpochSecond(0L, 1L);
        check(one.minusNanos(1L).equals(Instant.EPOCH), "1ns minus 1ns is epoch");
    }

    static void testLongMinValueArgument() {
        Instant result = Instant.EPOCH.minusNanos(Long.MIN_VALUE);
        check(result.equals(Instant.ofEpochSecond(9223372036L, 854775808L)),
                "minusNanos(Long.MIN_VALUE) from epoch");
    }

    static void testOutOfRangeResults() {
        expectDateTimeException(Instant.MAX, -1L);
        expectDateTimeException(Instant.MIN, 1L);
    }

    static void expectDateTimeException(Instant instant, long nanos) {
        try {
            instant.minusNanos(nanos);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException");
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("check failed: " + what);
        }
    }
}
