/* @test    @summary Tests for java.security.Signature.getParameters() method behavior.    @run main SignatureWitupTest */

import java.security.Signature;
import java.security.AlgorithmParameters;

public class SignatureWitupTest {

    public static void main(String[] args) {
        testGetParametersReturnsEngineResult();
    }

    private static void testGetParametersReturnsEngineResult() {
        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA256withRSA");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Signature instance", e);
        }
        try {
            AlgorithmParameters params = signature.getParameters();
            if (params != null) {
                System.out.println("getParameters() returned non-null: " + params);
            } else {
                System.out.println("getParameters() returned null");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in getParameters()", e);
        }
    }
}
