/* @test    @summary Test DerOutputStream.putInteger(int) method for correct DER encoding behavior    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutIntegerZero();
        testPutIntegerPositive();
        testPutIntegerNegative();
        testPutIntegerLarge();
    }

    static void testPutIntegerZero() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putInteger(0);
        byte[] result = dos.toByteArray();
        // Expected: {0x02, 0x01, 0x00} (tag, length, value)
        assert result.length == 3 : "Length mismatch for zero";
        assert result[0] == 0x02 : "Incorrect tag for integer";
        assert result[1] == 0x01 : "Incorrect length for zero";
        assert result[2] == 0x00 : "Incorrect value for zero";
    }

    static void testPutIntegerPositive() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putInteger(127);
        byte[] result = dos.toByteArray();
        // Expected: {0x02, 0x01, 0x7F}
        assert result.length == 3 : "Length mismatch for 127";
        assert result[0] == 0x02 : "Incorrect tag for integer";
        assert result[1] == 0x01 : "Incorrect length for 127";
        assert result[2] == 0x7F : "Incorrect value for 127";
    }

    static void testPutIntegerNegative() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putInteger(-1);
        byte[] result = dos.toByteArray();
        // For negative, DER uses two's complement; -1 is 0xFF
        assert result.length >= 3 : "Length mismatch for -1";
        assert result[0] == 0x02 : "Incorrect tag for integer";
        assert result[1] >= 0x01 : "Incorrect length for -1";
        assert result[result.length - 1] == (byte) 0xFF : "Incorrect value for -1";
    }

    static void testPutIntegerLarge() throws IOException {
        int largeInt = 0x7FFFFFFF; // Max positive int
        DerOutputStream dos = new DerOutputStream();
        dos.putInteger(largeInt);
        byte[] result = dos.toByteArray();
        // Should start with tag 0x02, length 4, value 0x7FFFFFFF
        assert result.length == 6 : "Length mismatch for large int";
        assert result[0] == 0x02 : "Incorrect tag for large int";
        assert result[1] == 0x04 : "Incorrect length for large int";
        assert result[2] == 0x7F : "Incorrect first byte";
        assert result[3] == (byte) 0xFF : "Incorrect second byte";
        assert result[4] == (byte) 0xFF : "Incorrect third byte";
        assert result[5] == (byte) 0xFF : "Incorrect fourth byte";
    }
}
