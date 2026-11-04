/* @test    @summary Test DerOutputStream.putEnumerated method for correct DER encoding    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutEnumeratedZero();
        testPutEnumeratedPositive();
        testPutEnumeratedNegative();
    }

    static void testPutEnumeratedZero() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putEnumerated(0);
        byte[] result = dos.toByteArray();
        byte[] expected = { (byte)0x0A, 1, 0 };
        assert java.util.Arrays.equals(result, expected) : "Failed: putEnumerated(0)";
    }

    static void testPutEnumeratedPositive() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        int value = 127;
        dos.putEnumerated(value);
        byte[] result = dos.toByteArray();
        byte[] expected = { (byte)0x0A, 1, (byte)127 };
        assert java.util.Arrays.equals(result, expected) : "Failed: putEnumerated(127)";
    }

    static void testPutEnumeratedNegative() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        int value = -1;
        dos.putEnumerated(value);
        byte[] result = dos.toByteArray();
        byte[] expected = { (byte)0x0A, 1, (byte)255 };
        assert java.util.Arrays.equals(result, expected) : "Failed: putEnumerated(-1)";
    }
}
