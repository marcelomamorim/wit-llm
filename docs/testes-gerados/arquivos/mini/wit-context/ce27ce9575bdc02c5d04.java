/*
 * @test
 * @summary Test Signature.getParameters() throws UnsupportedOperationException as expected
 * @run main SignatureWitupTest
 */

import java.security.Signature;
import java.security.AlgorithmParameters;

public class SignatureWitupTest {
    public static void main(String[] args) throws Exception {
        Signature sig = Signature.getInstance("SHA256withDSA");
        try {
            AlgorithmParameters params = sig.getParameters();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown, got: " + params);
        } catch (UnsupportedOperationException expected) {
            // Expected exception, test passes
        }
    }
}
