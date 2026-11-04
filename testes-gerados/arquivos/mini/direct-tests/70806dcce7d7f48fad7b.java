/*
 * @test
 * @summary Test java.time.Instant.ofEpochSecond(long) method
 * @run main InstantWitupTest
 */

public class InstantWitupTest {
    public static void main(String[] args) {
        testOfEpochSecondZero();
        testOfEpochSecondPositive();
        testOfEpochSecondNegative();
    }

    private static void testOfEpochSecondZero() {
        java.time.Instant instant = java.time.Instant.ofEpochSecond(0L);
        if (instant.getEpochSecond() != 0L) {
            throw new AssertionError("Expected epochSecond 0 but got " + instant.getEpochSecond());
        }
        if (instant.getNano() != 0) {
            throw new AssertionError("Expected nano 0 but got " + instant.getNano());
        }
    }

    private static void testOfEpochSecondPositive() {
        long epochSecond = 123456789L;
        java.time.Instant instant = java.time.Instant.ofEpochSecond(epochSecond);
        if (instant.getEpochSecond() != epochSecond) {
            throw new AssertionError("Expected epochSecond " + epochSecond + " but got " + instant.getEpochSecond());
        }
        if (instant.getNano() != 0) {
            throw new AssertionError("Expected nano 0 but got " + instant.getNano());
        }
    }

    private static void testOfEpochSecondNegative() {
        long epochSecond = -123456789L;
        java.time.Instant instant = java.time.Instant.ofEpochSecond(epochSecond);
        if (instant.getEpochSecond() != epochSecond) {
            throw new AssertionError("Expected epochSecond " + epochSecond + " but got " + instant.getEpochSecond());
        }
        if (instant.getNano() != 0) {
            throw new AssertionError("Expected nano 0 but got " + instant.getNano());
        }
    }
}
