/*
 * @test
 * @summary Witup-generated checks for CoderResult.unmappableForLength
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        int[] invalidLengths = {0, -1, Integer.MIN_VALUE};
        for (int len : invalidLengths) {
            try {
                CoderResult.unmappableForLength(len);
                throw new RuntimeException("Expected IllegalArgumentException for length " + len);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }

        int[] validLengths = {1, 2, 3, 4, 5, 8, 100};
        for (int len : validLengths) {
            CoderResult r = CoderResult.unmappableForLength(len);
            if (!r.isUnmappable()) {
                throw new RuntimeException("Not unmappable for length " + len);
            }
            if (!r.isError()) {
                throw new RuntimeException("Not an error for length " + len);
            }
            if (r.isMalformed() || r.isUnderflow() || r.isOverflow()) {
                throw new RuntimeException("Wrong category for length " + len);
            }
            if (r.length() != len) {
                throw new RuntimeException("Wrong length: expected " + len + " got " + r.length());
            }
            CoderResult again = CoderResult.unmappableForLength(len);
            if (r != again) {
                throw new RuntimeException("Expected cached instance for length " + len);
            }
        }
    }
}
