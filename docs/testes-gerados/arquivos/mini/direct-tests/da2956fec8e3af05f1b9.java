/*
 * @test
 * @summary Test MethodHandleInfo.referenceKindToString(int) for valid and invalid inputs
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;

public class MethodHandleInfoWitupTest {
    public static void main(String[] args) {
        testValidReferenceKinds();
        testInvalidReferenceKind();
    }

    private static void testValidReferenceKinds() {
        // Valid reference kinds are defined by MethodHandleNatives.refKindIsValid
        // Since we cannot access MethodHandleNatives directly, test known valid values 1 to 9
        for (int refKind = 1; refKind <= 9; refKind++) {
            String name = MethodHandleInfo.referenceKindToString(refKind);
            if (name == null || name.isEmpty()) {
                throw new AssertionError("referenceKindToString returned null or empty for valid refKind: " + refKind);
            }
        }
    }

    private static void testInvalidReferenceKind() {
        int[] invalidKinds = {0, 10, -1, 100};
        for (int invalid : invalidKinds) {
            try {
                MethodHandleInfo.referenceKindToString(invalid);
                throw new AssertionError("Expected IllegalArgumentException for invalid refKind: " + invalid);
            } catch (IllegalArgumentException iae) {
                // Expected
            }
        }
    }
}
