/* @test    @summary Test DerOutputStream.putNull() method for correct DER encoding of null values.    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutNullProducesCorrectEncoding();
    }

    private static void testPutNullProducesCorrectEncoding() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putNull();
        byte[] result = dos.toByteArray();
        byte[] expected = new byte[] { (byte) DerOutputStream.tag_Null, 0 };
        assert result.length == expected.length : "Result length mismatch";
        for (int i = 0; i < expected.length; i++) {
            assert result[i] == expected[i] : "Byte mismatch at position " + i;
        }
    }
}
