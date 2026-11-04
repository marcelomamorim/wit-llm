/*
 * @test
 * @summary Verifies Instant.plusMillis observable results, including negative and sub-second amounts
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testPositiveWholeSeconds();
        testSubSecondAddition();
        testNegativeMillisCrossesSecondBoundary();
        testZeroReturnsEquivalentInstant();
        testNanoCarryAcrossSecond();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPositiveWholeSeconds() {
        Instant result = Instant.ofEpochSecond(0).plusMillis(1500);
        check(result.getEpochSecond() == 1, "expected epochSecond 1 but was " + result.getEpochSecond());
        check(result.getNano() == 500_000_000, "expected nano 500000000 but was " + result.getNano());
    }

    static void testSubSecondAddition() {
        Instant result = Instant.ofEpochSecond(0).plusMillis(1001);
        check(result.getEpochSecond() == 1, "expected epochSecond 1 but was " + result.getEpochSecond());
        check(result.getNano() == 1_000_000, "expected nano 1000000 but was " + result.getNano());
    }

    static void testNegativeMillisCrossesSecondBoundary() {
        Instant result = Instant.ofEpochSecond(0).plusMillis(-1);
        check(result.getEpochSecond() == -1, "expected epochSecond -1 but was " + result.getEpochSecond());
        check(result.getNano() == 999_000_000, "expected nano 999000000 but was " + result.getNano());
    }

    static void testZeroReturnsEquivalentInstant() {
        Instant base = Instant.ofEpochSecond(10, 5);
        Instant result = base.plusMillis(0);
        check(result.equals(base), "plusMillis(0) should be equal to the original instant");
    }

    static void testNanoCarryAcrossSecond() {
        Instant result = Instant.ofEpochSecond(10, 5).plusMillis(2000);
        check(result.getEpochSecond() == 12, "expected epochSecond 12 but was " + result.getEpochSecond());
        check(result.getNano() == 5, "expected nano 5 but was " + result.getNano());
    }
}
