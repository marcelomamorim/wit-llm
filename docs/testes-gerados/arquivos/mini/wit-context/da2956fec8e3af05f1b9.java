/*
 * @test
 * @summary Test MethodHandleInfo.referenceKindToString(int) for valid and invalid inputs
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandleNatives;

public class MethodHandleInfoWitupTest {
    public static void main(String[] args) {
        testValidReferenceKinds();
        testInvalidReferenceKinds();
    }

    private static void testValidReferenceKinds() {
        // Test all valid reference kinds from REF_NONE+1 up to REF_LIMIT-1
        for (int rk = MethodHandleNatives.REF_NONE + 1; rk < MethodHandleNatives.REF_LIMIT; rk++) {
            if (!MethodHandleNatives.refKindIsValid(rk)) {
                throw new RuntimeException("refKindIsValid returned false for supposedly valid reference kind: " + rk);
            }
            String name = MethodHandleInfo.referenceKindToString(rk);
            if (name == null || name.isEmpty()) {
                throw new RuntimeException("referenceKindToString returned null or empty for valid reference kind: " + rk);
            }
        }
    }

    private static void testInvalidReferenceKinds() {
        // Test some invalid reference kinds: less than or equal to REF_NONE and greater or equal to REF_LIMIT
        int[] invalidKinds = {MethodHandleNatives.REF_NONE, MethodHandleNatives.REF_NONE - 1, 0, -1, MethodHandleNatives.REF_LIMIT, MethodHandleNatives.REF_LIMIT + 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int rk : invalidKinds) {
            if (MethodHandleNatives.refKindIsValid(rk)) {
                throw new RuntimeException("refKindIsValid returned true for invalid reference kind: " + rk);
            }
            try {
                MethodHandleInfo.referenceKindToString(rk);
                throw new RuntimeException("Expected IllegalArgumentException for invalid reference kind: " + rk);
            } catch (IllegalArgumentException e) {
                // Expected exception
                String msg = e.getMessage();
                if (msg == null || !msg.contains("invalid reference kind")) {
                    throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
                }
            }
        }
    }
}
