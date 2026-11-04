/*
 * @test
 * @summary Verify DER boolean encoding produced by DerOutputStream.putBoolean
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.putBoolean(true);
        check(out.toByteArray(), new byte[]{0x01, 0x01, (byte) 0xff});

        out = new DerOutputStream(16);
        out.putBoolean(false);
        check(out.toByteArray(), new byte[]{0x01, 0x01, 0x00});

        out = new DerOutputStream();
        out.putBoolean(true);
        out.putBoolean(false);
        check(out.toByteArray(), new byte[]{0x01, 0x01, (byte) 0xff, 0x01, 0x01, 0x00});
    }

    static void check(byte[] actual, byte[] expected) {
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException("Unexpected encoding: " + Arrays.toString(actual));
        }
    }
}
