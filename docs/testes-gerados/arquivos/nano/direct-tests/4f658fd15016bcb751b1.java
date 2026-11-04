/* @test    @summary Test DerOutputStream.putInteger(Integer) method    @run main DerOutputStreamWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutIntegerWithPositiveNumber();
        testPutIntegerWithZero();
        testPutIntegerWithNegativeNumber();
    }

    static void testPutIntegerWithPositiveNumber() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        Integer value = Integer.valueOf(12345);
        dos.putInteger(value);
        byte[] result = dos.toByteArray();
        // Expect the result to contain the DER encoding for integer 12345
        // DER encoding: 0x02 (INTEGER tag), length, value bytes
        assert result[0] == 0x02 : "Expected INTEGER tag 0x02";
        int length = result[1] & 0xFF;
        assert length == result.length - 2 : "Length byte does not match actual data length";
        // Check that the value bytes match 0x30 0x39 (which is 12345 in big-endian)
        byte[] expectedValueBytes = {0x30, 0x39};
        for (int i = 0; i < expectedValueBytes.length; i++) {
            assert result[2 + i] == expectedValueBytes[i] : "Value bytes do not match expected";
        }
    }

    static void testPutIntegerWithZero() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        Integer value = Integer.valueOf(0);
        dos.putInteger(value);
        byte[] result = dos.toByteArray();
        assert result[0] == 0x02 : "Expected INTEGER tag 0x02";
        int length = result[1] & 0xFF;
        assert length == result.length - 2 : "Length byte does not match actual data length";
        // Zero is encoded as 0x00
        assert result[2] == 0x00 : "Expected value byte 0x00 for zero";
    }

    static void testPutIntegerWithNegativeNumber() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        Integer value = Integer.valueOf(-12345);
        dos.putInteger(value);
        byte[] result = dos.toByteArray();
        assert result[0] == 0x02 : "Expected INTEGER tag 0x02";
        int length = result[1] & 0xFF;
        assert length == result.length - 2 : "Length byte does not match actual data length";
        // Negative number encoding check: first byte should have the sign bit set
        byte firstValueByte = result[2];
        assert (firstValueByte & 0x80) != 0 : "First value byte should have sign bit set for negative";
    }
}
