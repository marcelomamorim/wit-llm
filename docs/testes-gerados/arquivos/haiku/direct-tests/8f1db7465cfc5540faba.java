/*
 * @test
 * @summary AlgorithmParameters.init(byte[]) rejects re-initialization and leaves state unchanged after a failed decode
 * @run main AlgorithmParametersWitupTest
 */

import java.io.IOException;
import java.security.AlgorithmParameters;

public class AlgorithmParametersWitupTest {

    private static final byte[] VALID_DSA_PARAMS = new byte[] {
        0x30, 0x09,
        0x02, 0x01, 0x17,
        0x02, 0x01, 0x0B,
        0x02, 0x01, 0x04
    };

    private static final byte[] INVALID_DER = new byte[] { 0x01, 0x02 };

    public static void main(String[] args) throws Exception {
        testSecondInitRejected();
        testFailedInitDoesNotMarkInitialized();
        System.out.println("PASSED");
    }

    private static void testSecondInitRejected() throws Exception {
        AlgorithmParameters ap = AlgorithmParameters.getInstance("DSA");
        ap.init(VALID_DSA_PARAMS);
        boolean thrown = false;
        try {
            ap.init(VALID_DSA_PARAMS);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Second init() did not throw IOException");
        }
    }

    private static void testFailedInitDoesNotMarkInitialized() throws Exception {
        AlgorithmParameters ap = AlgorithmParameters.getInstance("DSA");
        boolean thrown = false;
        try {
            ap.init(INVALID_DER);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("init() with invalid DER did not throw IOException");
        }
        try {
            ap.init(VALID_DSA_PARAMS);
        } catch (IOException e) {
            throw new RuntimeException("init() after failed init was rejected as already initialized", e);
        }
    }
}
