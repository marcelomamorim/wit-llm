/*
 * @test
 * @summary Test XECPrivateKeySpec constructor and defensive copying
 * @run main XECPrivateKeySpecWitupTest
 */

import java.security.spec.XECPrivateKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;
import java.math.BigInteger;
import java.util.Objects;

public class XECPrivateKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testNullParamsThrows();
        testNullScalarThrows();
        testDefensiveCopying();
        testGettersReturnCorrectValues();
    }

    private static void testNullParamsThrows() {
        byte[] scalar = new byte[] {1, 2, 3};
        try {
            new XECPrivateKeySpec(null, scalar);
            throw new RuntimeException("Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            if (!"params must not be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null params", e);
            }
        }
    }

    private static void testNullScalarThrows() {
        AlgorithmParameterSpec params = createDummyParams();
        try {
            new XECPrivateKeySpec(params, null);
            throw new RuntimeException("Expected NullPointerException for null scalar");
        } catch (NullPointerException e) {
            if (!"scalar must not be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null scalar", e);
            }
        }
    }

    private static void testDefensiveCopying() {
        AlgorithmParameterSpec params = createDummyParams();
        byte[] scalar = new byte[] {10, 20, 30};
        XECPrivateKeySpec spec = new XECPrivateKeySpec(params, scalar);

        // Modify original scalar after construction
        scalar[0] = 99;

        byte[] retrievedScalar = spec.getScalar();
        if (retrievedScalar.length != 3 || retrievedScalar[0] != 10 || retrievedScalar[1] != 20 || retrievedScalar[2] != 30) {
            throw new RuntimeException("Constructor did not defensively copy scalar array");
        }

        // Modify retrieved scalar and check internal state is not affected
        retrievedScalar[1] = 77;
        byte[] retrievedScalar2 = spec.getScalar();
        if (retrievedScalar2[1] != 20) {
            throw new RuntimeException("getScalar() did not return a fresh copy");
        }
    }

    private static void testGettersReturnCorrectValues() {
        AlgorithmParameterSpec params = createDummyParams();
        byte[] scalar = new byte[] {5, 6, 7};
        XECPrivateKeySpec spec = new XECPrivateKeySpec(params, scalar);

        if (spec.getParams() != params) {
            throw new RuntimeException("getParams() did not return the same params instance");
        }

        byte[] scalarCopy = spec.getScalar();
        if (scalarCopy.length != 3 || scalarCopy[0] != 5 || scalarCopy[1] != 6 || scalarCopy[2] != 7) {
            throw new RuntimeException("getScalar() did not return correct scalar bytes");
        }
    }

    private static AlgorithmParameterSpec createDummyParams() {
        // Create a minimal ECParameterSpec instance for testing
        // Use a simple curve with prime field
        EllipticCurve curve = new EllipticCurve(new ECFieldFp(BigInteger.valueOf(23)), BigInteger.valueOf(1), BigInteger.valueOf(1));
        return new ECParameterSpec(curve, null, null, 1);
    }
}
