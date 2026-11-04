/*
 * @test
 * @summary Verifies DER INTEGER encoding produced by DerOutputStream.putInteger for boundary and sign-sensitive values
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(0, new byte[]{0x02, 0x01, 0x00});
        check(1, new byte[]{0x02, 0x01, 0x01});
        check(127, new byte[]{0x02, 0x01, 0x7f});
        check(128, new byte[]{0x02, 0x02, 0x00, (byte) 0x80});
        check(-1, new byte[]{0x02, 0x01, (byte) 0xff});
        check(-128, new byte[]{0x02, 0x01, (byte) 0x80});
        check(-129, new byte[]{0x02, 0x02, (byte) 0xff, (byte) 0x7f});
        check(Integer.MAX_VALUE, new byte[]{0x02, 0x04, 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        check(Integer.MIN_VALUE, new byte[]{0x02, 0x04, (byte) 0x80, 0x00, 0x00, 0x00});

        DerOutputStream sized = new DerOutputStream(16);
        sized.putInteger(5);
        if (!Arrays.equals(sized.toByteArray(), new byte[]{0x02, 0x01, 0x05})) {
            throw new RuntimeException("Sized stream encoding mismatch");
        }

        DerOutputStream multi = new DerOutputStream();
        multi.putInteger(0);
        multi.putInteger(128);
        byte[] expected = new byte[]{0x02, 0x01, 0x00, 0x02, 0x02, 0x00, (byte) 0x80};
        if (!Arrays.equals(multi.toByteArray(), expected)) {
            throw new RuntimeException("Consecutive putInteger encoding mismatch");
        }
    }

    private static void check(int value, byte[] expected) throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.putInteger(value);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException("putInteger(" + value + ") produced "
                    + Arrays.toString(actual) + ", expected " + Arrays.toString(expected));
        }
    }
}
