/*
 * @test
 * @summary Test constructor and accessors of XECPrivateKeySpec
 * @run main XECPrivateKeySpecWitupTest
 */

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.XECPrivateKeySpec;

public class XECPrivateKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorNullParams();
        testConstructorNullScalar();
        testConstructorAndAccessors();
    }

    private static void testConstructorNullParams() {
        byte[] scalar = new byte[] {1, 2, 3};
        try {
            new XECPrivateKeySpec(null, scalar);
            throw new RuntimeException("Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testConstructorNullScalar() {
        AlgorithmParameterSpec params = new DummyAlgorithmParameterSpec();
        try {
            new XECPrivateKeySpec(params, null);
            throw new RuntimeException("Expected NullPointerException for null scalar");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testConstructorAndAccessors() {
        AlgorithmParameterSpec params = new DummyAlgorithmParameterSpec();
        byte[] scalar = new byte[] {10, 20, 30, 40};
        XECPrivateKeySpec keySpec = new XECPrivateKeySpec(params, scalar);

        if (keySpec.getParams() != params) {
            throw new RuntimeException("getParams() did not return the expected params instance");
        }

        byte[] returnedScalar = keySpec.getScalar();
        if (returnedScalar == scalar) {
            throw new RuntimeException("getScalar() must return a new copy, not the original array");
        }
        if (returnedScalar.length != scalar.length) {
            throw new RuntimeException("Returned scalar length mismatch");
        }
        for (int i = 0; i < scalar.length; i++) {
            if (returnedScalar[i] != scalar[i]) {
                throw new RuntimeException("Returned scalar content mismatch at index " + i);
            }
        }

        // Modify returned array and verify original is not affected
        returnedScalar[0] = (byte) (returnedScalar[0] + 1);
        byte[] returnedScalar2 = keySpec.getScalar();
        if (returnedScalar2[0] != scalar[0]) {
            throw new RuntimeException("Original scalar array was modified through getScalar() return value");
        }
    }

    // Minimal dummy implementation to satisfy AlgorithmParameterSpec parameter
    private static class DummyAlgorithmParameterSpec implements AlgorithmParameterSpec {
    }
}
