/*
 * @test
 * @summary GCMParameterSpec(int, byte[]) rejects null IV source and negative tLen, and accepts a valid IV
 * @run main GCMParameterSpecWitupTest
 */

import javax.crypto.spec.GCMParameterSpec;

public class GCMParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullSrcThrowsIAE();
        testNegativeTLenThrowsIAE();
        testValidArgumentsDoNotThrow();
    }

    static void testNullSrcThrowsIAE() {
        try {
            new GCMParameterSpec(128, null);
            throw new RuntimeException("Expected IllegalArgumentException for null src");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testNegativeTLenThrowsIAE() {
        try {
            new GCMParameterSpec(-1, new byte[12]);
            throw new RuntimeException("Expected IllegalArgumentException for negative tLen");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testValidArgumentsDoNotThrow() {
        new GCMParameterSpec(128, new byte[12]);
    }
}
