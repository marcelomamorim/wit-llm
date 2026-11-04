/*
 * @test
 * @summary Verify that DerOutputStream.putNull() appends the DER NULL tag (0x05) followed by a zero length (0x00)
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import java.util.Arrays;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSinglePutNull();
        testRepeatedPutNullAppends();
        testPutNullWithPresizedStream();
    }

    static void testSinglePutNull() throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.putNull();
        byte[] expected = {0x05, 0x00};
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Expected [05 00] but got " + Arrays.toString(actual));
        }
        if (out.size() != 2) {
            throw new RuntimeException("Expected size 2 but got " + out.size());
        }
    }

    static void testRepeatedPutNullAppends() throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.putNull();
        out.putNull();
        byte[] expected = {0x05, 0x00, 0x05, 0x00};
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Expected [05 00 05 00] but got " + Arrays.toString(actual));
        }
    }

    static void testPutNullWithPresizedStream() throws Exception {
        DerOutputStream out = new DerOutputStream(16);
        out.putNull();
        byte[] expected = {0x05, 0x00};
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Expected [05 00] with presized stream but got " + Arrays.toString(actual));
        }
    }
}
