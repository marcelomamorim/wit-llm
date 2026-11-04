/*
 * @test
 * @summary Test AlgorithmParameters.init(byte[]) for correct initialization and exception on re-init
 * @run main AlgorithmParametersWitupTest
 */

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;

public class AlgorithmParametersWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain an AlgorithmParameters instance for a standard algorithm
        AlgorithmParameters params = null;
        try {
            params = AlgorithmParameters.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("AES AlgorithmParameters not available", e);
        }

        // Test that init(byte[]) initializes successfully when not initialized
        byte[] dummyParams = new byte[0];
        params.init(dummyParams); // should succeed first time

        // Test that calling init(byte[]) again throws IOException
        boolean threwIOException = false;
        try {
            params.init(dummyParams);
        } catch (IOException e) {
            threwIOException = true;
        }

        if (!threwIOException) {
            throw new RuntimeException("Expected IOException when re-initializing AlgorithmParameters");
        }

        // Test getAlgorithm returns non-null and equals "AES"
        String alg = params.getAlgorithm();
        if (alg == null || !alg.equals("AES")) {
            throw new RuntimeException("AlgorithmParameters.getAlgorithm() returned unexpected value: " + alg);
        }

        System.out.println("Test passed");
    }
}
