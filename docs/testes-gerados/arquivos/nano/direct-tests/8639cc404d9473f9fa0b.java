/* @test    @summary Test init(byte[] params, String format) method of AlgorithmParameters for successful initialization and exception when re-initialized    @run main AlgorithmParametersWitupTest */

import java.security.AlgorithmParameters;
import java.io.IOException;

public class AlgorithmParametersWitupTest {

    public static void main(String[] args) {
        testInitSuccess();
        testInitAlreadyInitialized();
    }

    private static void testInitSuccess() {
        byte[] params = {1, 2, 3};
        String format = "ASN.1";
        AlgorithmParameters ap = new AlgorithmParameters(null, null, "TestAlgorithm");
        try {
            ap.init(params, format);
            System.out.println("testInitSuccess passed");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception in testInitSuccess", e);
        }
    }

    private static void testInitAlreadyInitialized() {
        byte[] params = {4, 5, 6};
        String format = "X509";
        AlgorithmParameters ap = new AlgorithmParameters(null, null, "TestAlgorithm");
        try {
            ap.init(params, format);
            ap.init(params, format);
            throw new RuntimeException("Expected IOException was not thrown");
        } catch (IOException e) {
            if ("already initialized".equals(e.getMessage())) {
                System.out.println("testInitAlreadyInitialized passed");
            } else {
                throw new RuntimeException("Unexpected exception message", e);
            }
        }
    }
}
