/*
 * @test
 * @summary Verify CoderResult.malformedForLength caching, result state and argument validation
 * @run main CoderResultWitupTest
 */

public class CoderResultWitupTest {

    public static void main(String[] args) {
        for (int n = 1; n <= 4; n++) {
            CoderResult a = CoderResult.malformedForLength(n);
            CoderResult b = CoderResult.malformedForLength(n);
            check(a == b, "same instance expected for length " + n);
            check(a.isMalformed(), "isMalformed expected for length " + n);
            check(a.isError(), "isError expected for length " + n);
            check(!a.isUnderflow() && !a.isOverflow(), "not underflow/overflow for length " + n);
            check(a.length() == n, "length mismatch for " + n);
        }

        int[] larger = {5, 16, 1024};
        for (int n : larger) {
            CoderResult a = CoderResult.malformedForLength(n);
            CoderResult b = CoderResult.malformedForLength(n);
            check(a == b, "cached instance expected for length " + n);
            check(a.isMalformed(), "isMalformed expected for length " + n);
            check(a.isError(), "isError expected for length " + n);
            check(a.length() == n, "length mismatch for " + n);
        }

        int[] invalid = {0, -1, Integer.MIN_VALUE};
        for (int v : invalid) {
            boolean thrown = false;
            try {
                CoderResult.malformedForLength(v);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "IllegalArgumentException expected for length " + v);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
