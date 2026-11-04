/*
 * @test
 * @summary Tests argument validation and observable results of CoderResult.malformedForLength
 * @run main CoderResultWitupTest
 */

public class CoderResultWitupTest {

    public static void main(String[] args) {
        testNonPositiveLengthThrows();
        testSmallLengths();
        testLargeLengthsAndCache();
    }

    static void testNonPositiveLengthThrows() {
        int[] bad = {0, -1, Integer.MIN_VALUE};
        for (int n : bad) {
            boolean thrown = false;
            try {
                CoderResult.malformedForLength(n);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "expected IllegalArgumentException for length " + n);
        }
    }

    static void testSmallLengths() {
        for (int n = 1; n <= 4; n++) {
            CoderResult r = CoderResult.malformedForLength(n);
            check(r.isMalformed(), "not malformed for length " + n);
            check(r.isError(), "not error for length " + n);
            check(!r.isUnderflow(), "unexpected underflow for length " + n);
            check(!r.isOverflow(), "unexpected overflow for length " + n);
            check(r.length() == n, "wrong length for " + n + ": " + r.length());
            check(r == CoderResult.malformedForLength(n), "cached instance differs for length " + n);
        }
    }

    static void testLargeLengthsAndCache() {
        int[] lens = {5, 16, 1024};
        for (int n : lens) {
            CoderResult r = CoderResult.malformedForLength(n);
            check(r.isMalformed(), "not malformed for length " + n);
            check(r.isError(), "not error for length " + n);
            check(r.length() == n, "wrong length for " + n + ": " + r.length());
            CoderResult again = CoderResult.malformedForLength(n);
            check(r == again, "expected same cached instance for length " + n);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
