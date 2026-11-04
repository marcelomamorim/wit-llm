/*
 * @test
 * @summary Test AlgorithmParameters.init(byte[], String) for correct initialization and exception on re-init
 * @run main AlgorithmParametersWitupTest
 */

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;

public class AlgorithmParametersWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain an instance for a standard algorithm (AES is guaranteed by spec)
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");

        // Prepare dummy parameters for init - empty byte array is minimal valid input
        byte[] dummyParams = new byte[0];

        // First init should succeed
        params.init(dummyParams, "ASN.1");

        // Confirm algorithm name is as requested
        if (!"AES".equals(params.getAlgorithm())) {
            throw new RuntimeException("Algorithm name mismatch");
        }

        // Second init should throw IOException because already initialized
        boolean threw = false;
        try {
            params.init(dummyParams, "ASN.1");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("already initialized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException not thrown on re-init");
        }
    }
}
