/*
 * @test
 * @summary Test java.security.AlgorithmParameters init(byte[], String) method
 * @run main AlgorithmParametersWitupTest
 */

import java.security.AlgorithmParameters;
import java.security.Provider;
import java.security.Security;
import java.io.IOException;

public class AlgorithmParametersWitupTest {

    public static void main(String[] args) throws Exception {
        testInitSuccess();
        testInitAlreadyInitializedThrows();
    }

    private static void testInitSuccess() throws Exception {
        // Obtain an AlgorithmParameters instance for a known algorithm
        AlgorithmParameters ap = AlgorithmParameters.getInstance("AES");

        // Prepare some dummy parameters (empty byte array) and format
        byte[] params = new byte[0];
        String format = "ASN.1"; // common format string, may be accepted

        // Call init - should succeed without exception
        ap.init(params, format);

        // Verify that getAlgorithm returns the expected algorithm
        if (!"AES".equals(ap.getAlgorithm())) {
            throw new RuntimeException("Algorithm name mismatch after init");
        }
    }

    private static void testInitAlreadyInitializedThrows() throws Exception {
        AlgorithmParameters ap = AlgorithmParameters.getInstance("AES");
        byte[] params = new byte[0];
        String format = "ASN.1";

        // First init should succeed
        ap.init(params, format);

        // Second init should throw IOException with message "already initialized"
        try {
            ap.init(params, format);
            throw new RuntimeException("Expected IOException not thrown on second init");
        } catch (IOException e) {
            if (!"already initialized".equals(e.getMessage())) {
                throw new RuntimeException("IOException message mismatch: " + e.getMessage());
            }
        }
    }
}
