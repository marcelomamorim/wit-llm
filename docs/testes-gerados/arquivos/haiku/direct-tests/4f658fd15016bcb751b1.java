/*
 * @test
 * @summary Verifies DER INTEGER encoding produced by DerOutputStream.putInteger(Integer) and its null handling
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(Integer.valueOf(0), new byte[]{0x02, 0x01, 0x00});
        check(Integer.valueOf(127), new byte[]{0x02, 0x01, 0x7F});
        check(Integer.valueOf(-1), new byte[]{0x02, 0x01, (byte) 0xFF});
        check(Integer.valueOf(-128), new byte[]{0x02, 0x01, (byte) 0x80});
        check(Integer.valueOf(128), new byte[]{0x02, 0x02, 0x00, (byte) 0x80});
        check(Integer.valueOf(256), new byte[]{0x02, 0x02, 0x01, 0x00});

        DerOutputStream out = new DerOutputStream();
        boolean npe = false;
        try {
            out.putInteger((Integer) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null Integer");
        }
    }

    static void check(Integer value, byte[] expected) throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.putInteger(value);
        byte[] got = out.toByteArray();
        if (!Arrays.equals(got, expected)) {
            throw new RuntimeException("Unexpected DER encoding for " + value
                    + ": " + Arrays.toString(got)
                    + " expected " + Arrays.toString(expected));
        }
    }
}
