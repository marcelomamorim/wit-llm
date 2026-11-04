/*
 * @test
 * @summary Tests observable behavior of Mac.doFinal()
 * @run main MacWitupTest
 */

import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testDoFinalWithoutInit();
        testLengthAndReset();
    }

    static void testDoFinalWithoutInit() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        boolean thrown = false;
        try {
            mac.doFinal();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when MAC not initialized");
        }
    }

    static void testLengthAndReset() throws Exception {
        SecretKeySpec key = new SecretKeySpec("key".getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);

        byte[] empty = mac.doFinal();
        if (empty.length != 32) {
            throw new RuntimeException("Unexpected HmacSHA256 length: " + empty.length);
        }

        mac.update("abc".getBytes(StandardCharsets.UTF_8));
        byte[] withData = mac.doFinal();
        if (Arrays.equals(empty, withData)) {
            throw new RuntimeException("MAC of data should differ from MAC of empty input");
        }

        byte[] again = mac.doFinal();
        if (!Arrays.equals(empty, again)) {
            throw new RuntimeException("doFinal() should reset state so next result equals empty-input MAC");
        }
    }
}
