/* @test    @summary Test for GCMParameterSpec constructor with valid and invalid inputs    @run main GCMParameterSpecWitupTest */

import javax.crypto.spec.GCMParameterSpec;

public class GCMParameterSpecWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullSrcThrowsException();
        testNegativeTLenThrowsException();
    }

    private static void testValidConstructor() {
        byte[] iv = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int tLen = 128;
        GCMParameterSpec spec = new GCMParameterSpec(tLen, iv);
        assert spec != null : "Constructor with valid inputs should not be null";
    }

    private static void testNullSrcThrowsException() {
        boolean threw = false;
        try {
            new GCMParameterSpec(128, null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assert threw : "Constructor with null src should throw IllegalArgumentException";
    }

    private static void testNegativeTLenThrowsException() {
        byte[] iv = {0, 1, 2};
        boolean threw = false;
        try {
            new GCMParameterSpec(-1, iv);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assert threw : "Constructor with negative tLen should throw IllegalArgumentException";
    }
}
