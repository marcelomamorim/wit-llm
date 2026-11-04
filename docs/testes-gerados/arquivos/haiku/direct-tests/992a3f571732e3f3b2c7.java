/*
 * @test
 * @summary Witup tests for CoderResult.unmappableForLength
 * @run main CoderResultWitupTest
 */

public class CoderResultWitupTest {

    public static void main(String[] args) {
        for (int len = 1; len <= 8; len++) {
            CoderResult cr = CoderResult.unmappableForLength(len);
            check(cr.isUnmappable(), "not unmappable for length " + len);
            check(cr.isError(), "isError should be true for length " + len);
            check(!cr.isMalformed(), "isMalformed should be false for length " + len);
            check(!cr.isUnderflow(), "isUnderflow should be false for length " + len);
            check(!cr.isOverflow(), "isOverflow should be false for length " + len);
            check(cr.length() == len, "length mismatch for " + len + ": " + cr.length());
            check(cr == CoderResult.unmappableForLength(len),
                  "expected unique instance for length " + len);
        }
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);
    }

    static void expectIllegalArgument(int len) {
        try {
            CoderResult.unmappableForLength(len);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for length " + len);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
