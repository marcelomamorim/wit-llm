/*
 * @test
 * @summary Verify DerOutputStream.putEnumerated writes the ENUMERATED tag, a minimal two's complement length, and content bytes
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        check(0, new byte[]{0x0A, 0x01, 0x00});
        check(127, new byte[]{0x0A, 0x01, 0x7F});
        check(128, new byte[]{0x0A, 0x02, 0x00, (byte) 0x80});
        check(-1, new byte[]{0x0A, 0x01, (byte) 0xFF});
        check(256, new byte[]{0x0A, 0x02, 0x01, 0x00});
        check(-129, new byte[]{0x0A, 0x02, (byte) 0xFF, 0x7F});
    }

    static void check(int value, byte[] expected) throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.putEnumerated(value);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("putEnumerated(" + value + ") produced "
                    + Arrays.toString(actual) + ", expected " + Arrays.toString(expected));
        }
    }
}
