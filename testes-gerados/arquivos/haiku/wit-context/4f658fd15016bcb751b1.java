/*
 * @test
 * @summary Verifies DER INTEGER encoding through DerOutputStream.putInteger(Integer) and null handling
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        checkEncoding(5, new byte[]{0x02, 0x01, 0x05});
        checkEncoding(0, new byte[]{0x02, 0x01, 0x00});
        checkEncoding(-1, new byte[]{0x02, 0x01, (byte) 0xFF});
        checkEncoding(128, new byte[]{0x02, 0x02, 0x00, (byte) 0x80});
        checkEncoding(-129, new byte[]{0x02, 0x02, (byte) 0xFF, (byte) 0x7F});

        DerOutputStream out = new DerOutputStream();
        try {
            out.putInteger((Integer) null);
            throw new RuntimeException("expected NullPointerException for null Integer");
        } catch (NullPointerException expected) {
        }
    }

    static void checkEncoding(int value, byte[] expected) throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.putInteger(Integer.valueOf(value));
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException("unexpected DER encoding for " + value
                    + ": " + Arrays.toString(actual));
        }
    }
}
