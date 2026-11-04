/* @test    @summary Test for AlgorithmParameters.init(byte[], String) method to verify exception handling when re-initialized.    @run main AlgorithmParametersWitupTest */

import java.security.AlgorithmParameters;
import java.io.IOException;

public class AlgorithmParametersWitupTest {

    public static void main(String[] args) {
        testInitThrowsIOExceptionWhenReinitialized();
    }

    private static void testInitThrowsIOExceptionWhenReinitialized() {
        AlgorithmParameters params = null;
        try {
            params = AlgorithmParameters.getInstance("AES");
        } catch (Exception e) {
            // Fail the test if instance creation fails
            throw new RuntimeException("Failed to get AlgorithmParameters instance", e);
        }
        byte[] dummyBytes = {0x01, 0x02, 0x03};
        String format = "RAW";
        try {
            // First initialization should succeed
            params.init(dummyBytes, format);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during first init", e);
        }
        // Second initialization should throw IOException
        boolean exceptionThrown = false;
        try {
            params.init(dummyBytes, format);
        } catch (IOException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException was not thrown on re-initialization");
        }
    }
}
