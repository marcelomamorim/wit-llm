/*
 * @test
 * @summary Verifies Instant.minusMillis for normal, negative, zero and Long.MIN_VALUE inputs
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testPositiveMillis();
        testNegativeMillis();
        testZeroMillis();
        testLongMinValueMillis();
        testConsistencyWithPlusMillis();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPositiveMillis() {
        Instant result = Instant.ofEpochSecond(10, 0).minusMillis(1500);
        check(result.getEpochSecond() == 8L, "expected epochSecond 8 but was " + result.getEpochSecond());
        check(result.getNano() == 500000000, "expected nano 500000000 but was " + result.getNano());
    }

    static void testNegativeMillis() {
        Instant result = Instant.ofEpochSecond(10, 0).minusMillis(-1500);
        check(result.getEpochSecond() == 11L, "expected epochSecond 11 but was " + result.getEpochSecond());
        check(result.getNano() == 500000000, "expected nano 500000000 but was " + result.getNano());
    }

    static void testZeroMillis() {
        Instant base = Instant.ofEpochSecond(42, 123456789);
        Instant result = base.minusMillis(0);
        check(result.equals(base), "minusMillis(0) should return an equal instant");
    }

    static void testLongMinValueMillis() {
        Instant result = Instant.ofEpochSecond(0, 0).minusMillis(Long.MIN_VALUE);
        check(result.getEpochSecond() == 9223372036854775L,
              "expected epochSecond 9223372036854775 but was " + result.getEpochSecond());
        check(result.getNano() == 808000000,
              "expected nano 808000000 but was " + result.getNano());
    }

    static void testConsistencyWithPlusMillis() {
        Instant start = Instant.ofEpochSecond(0, 0);
        Instant expected = start.plusMillis(Long.MAX_VALUE).plusMillis(1);
        Instant actual = start.minusMillis(Long.MIN_VALUE);
        check(actual.equals(expected), "minusMillis(Long.MIN_VALUE) should equal plusMillis(Long.MAX_VALUE).plusMillis(1)");
    }
}
