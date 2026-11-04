/*
 * @test
 * @summary Test for MethodHandleInfo.referenceKindToString(int)
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;

public class MethodHandleInfoWitupTest {

    public static void main(String[] args) {
        testReferenceKindToStringValid();
        testReferenceKindToStringInvalid();
    }

    private static void testReferenceKindToStringValid() {
        // Test with valid reference kinds, assuming valid range from 0 to 7
        for (int kind = 0; kind <= 7; kind++) {
            try {
                String result = MethodHandleInfo.referenceKindToString(kind);
                System.out.println("referenceKindToString(" + kind + ") = " + result);
            } catch (IllegalArgumentException e) {
                System.err.println("Unexpected exception for valid kind " + kind + ": " + e.getMessage());
            }
        }
    }

    private static void testReferenceKindToStringInvalid() {
        int[] invalidKinds = {-1, 8, Integer.MAX_VALUE};
        for (int invalidKind : invalidKinds) {
            try {
                MethodHandleInfo.referenceKindToString(invalidKind);
                System.err.println("Expected exception not thrown for invalid kind " + invalidKind);
            } catch (IllegalArgumentException e) {
                System.out.println("Correctly caught exception for invalid kind " + invalidKind + ": " + e.getMessage());
            }
        }
    }
}
