/* @test    @summary Test for IOUtils.readNBytes method with normal and edge cases    @run main IOUtilsWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtilsWitupTest {

    public static void main(String[] args) throws IOException {
        testReadNBytesNormal();
        testReadNBytesZeroLength();
        testReadNBytesNegativeLength();
        testReadNBytesEOF();
    }

    static void testReadNBytesNormal() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = sun.security.util.IOUtils.readNBytes(is, 3);
        assert result.length == 3 : "Expected length 3, got " + result.length;
        assert result[0] == 1 && result[1] == 2 && result[2] == 3 : "Unexpected data in result";
    }

    static void testReadNBytesZeroLength() throws IOException {
        byte[] data = {1, 2, 3};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = sun.security.util.IOUtils.readNBytes(is, 0);
        assert result.length == 0 : "Expected length 0, got " + result.length;
    }

    static void testReadNBytesNegativeLength() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        try {
            sun.security.util.IOUtils.readNBytes(is, -1);
            assert false : "Expected IOException for negative length";
        } catch (IOException e) {
            assert e.getMessage().contains("length cannot be negative") : "Unexpected exception message: " + e.getMessage();
        }
    }

    static void testReadNBytesEOF() throws IOException {
        byte[] data = {1, 2};
        InputStream is = new ByteArrayInputStream(data);
        try {
            sun.security.util.IOUtils.readNBytes(is, 5);
            assert false : "Expected IOException for premature EOF";
        } catch (IOException e) {
            assert e.getMessage().contains("length") || e.getMessage().contains("premature EOF") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
