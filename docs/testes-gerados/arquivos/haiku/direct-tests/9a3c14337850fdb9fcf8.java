/*
 * @test
 * @summary Verifies DerOutputStream.putBoolean encodes boolean values as DER BOOLEAN TLV
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import java.util.Arrays;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        DerOutputStream t = new DerOutputStream();
        t.putBoolean(true);
        check(Arrays.equals(t.toByteArray(),
                new byte[]{0x01, 0x01, (byte) 0xff}), "true encoding");

        DerOutputStream f = new DerOutputStream();
        f.putBoolean(false);
        check(Arrays.equals(f.toByteArray(),
                new byte[]{0x01, 0x01, 0x00}), "false encoding");

        DerOutputStream m = new DerOutputStream(16);
        m.putBoolean(true);
        m.putBoolean(false);
        check(Arrays.equals(m.toByteArray(),
                new byte[]{0x01, 0x01, (byte) 0xff, 0x01, 0x01, 0x00}), "sequential encoding");
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Failed: " + msg);
        }
    }
}
