/* @test    @summary Testa o construtor de XECPrivateKeySpec para comportamentos observáveis e exceções.    @run main XECPrivateKeySpecWitupTest */

import java.security.spec.XECPrivateKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class XECPrivateKeySpecWitupTest {

    public static void main(String[] args) {
        testNullParams();
        testNullScalar();
    }

    private static void testNullParams() {
        byte[] scalar = {0, 1, 2};
        try {
            new XECPrivateKeySpec(null, scalar);
            System.err.println("Failed: Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testNullScalar() {
        AlgorithmParameterSpec params = new DummyAlgorithmParameterSpec();
        try {
            new XECPrivateKeySpec(params, null);
            System.err.println("Failed: Expected NullPointerException for null scalar");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static class DummyAlgorithmParameterSpec implements AlgorithmParameterSpec {
        // Dummy implementation for testing
    }
}
