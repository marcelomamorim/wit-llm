/*
 * @test
 * @summary Verify AlgorithmParameters.init(byte[]) rejects re-initialization with IOException and keeps the encoding
 * @run main AlgorithmParametersWitupTest
 */

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.util.Arrays;

public class AlgorithmParametersWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] iv = new byte[16];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = (byte) i;
        }
        byte[] encoded = new byte[18];
        encoded[0] = 0x04;
        encoded[1] = 0x10;
        System.arraycopy(iv, 0, encoded, 2, iv.length);

        AlgorithmParameters ap = AlgorithmParameters.getInstance("AES");
        ap.init(encoded);

        if (!"AES".equals(ap.getAlgorithm())) {
            throw new RuntimeException("unexpected algorithm: " + ap.getAlgorithm());
        }
        if (!Arrays.equals(encoded, ap.getEncoded())) {
            throw new RuntimeException("encoding mismatch after init");
        }

        try {
            ap.init(encoded);
            throw new RuntimeException("expected IOException on second init");
        } catch (IOException expected) {
            // expected: already initialized
        }

        if (!Arrays.equals(encoded, ap.getEncoded())) {
            throw new RuntimeException("encoding changed after rejected re-init");
        }
    }
}
