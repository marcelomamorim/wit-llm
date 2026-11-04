/*
 * @test
 * @summary Verifies DER INTEGER encoding produced by DerOutputStream.putInteger(int)
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
        check(127, new byte[]{0x02, 0x01, 0x7F});
        check(128, new byte[]{0x02, 0x02, 0x00, (byte) 0x80});
        check(255, new byte[]{0x02, 0x02, 0x00, (byte) 0xFF});
        check(-1, new byte[]{0x02, 0x01, (byte) 0xFF});
        check(-128, new byte[]{0x02, 0x01, (byte) 0x80});
        check(-129, new byte[]{0x02, 0x02, (byte) 0xFF, 0x7F});
        check(Integer.MAX_VALUE, new byte[]{0x02, 0x04, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        check(Integer.MIN_VALUE, new byte[]{0x02, 0x04, (byte) 0x80, 0x00, 0x00, 0x00});

        checkWithSize(5, new byte[]{0x02, 0x01, 0x05});
        checkWithSize(-2, new byte[]{0x02, 0x01, (byte) 0xFE});

        DerOutputStream out = new DerOutputStream();
        out.putInteger(1);
        out.putInteger(-1);
        byte[] expected = new byte[]{0x02, 0x01, 0x01, 0x02, 0x01, (byte) 0xFF};
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Appended INTEGER encodings mismatch: " + hex(actual));
        }
    }

    private static void check(int value, byte[] expected) throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.putInteger(value);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("putInteger(" + value + ") mismatch: " + hex(actual)
                    + " expected " + hex(expected));
        }
    }

    private static void checkWithSize(int value, byte[] expected) throws IOException {
        DerOutputStream out = new DerOutputStream(16);
        out.putInteger(value);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("sized putInteger(" + value + ") mismatch: " + hex(actual)
                    + " expected " + hex(expected));
        }
    }

    private static String hex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02X ", b & 0xFF));
        }
        return sb.toString().trim();
    }
}
