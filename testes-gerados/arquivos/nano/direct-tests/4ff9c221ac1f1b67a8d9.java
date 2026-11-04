/* @test    @summary Test constructor of PushbackInputStream with valid and invalid sizes    @run main PushbackInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.io.InputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorValidSize();
        testConstructorZeroSize();
        testConstructorNegativeSize();
    }

    static void testConstructorValidSize() {
        byte[] data = {1, 2, 3};
        InputStream in = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(in, 5);
        assert pbis != null : "PushbackInputStream should be created with valid size";
        assert pbis.buf.length == 5 : "Buffer length should match the size argument";
        assert pbis.pos == 5 : "Initial position should be equal to buffer size";
    }

    static void testConstructorZeroSize() {
        byte[] data = {1, 2, 3};
        InputStream in = new ByteArrayInputStream(data);
        try {
            new PushbackInputStream(in, 0);
            assert false : "Expected IllegalArgumentException for size 0";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("size  0") : "Exception message should indicate size  0";
        }
    }

    static void testConstructorNegativeSize() {
        byte[] data = {1, 2, 3};
        InputStream in = new ByteArrayInputStream(data);
        try {
            new PushbackInputStream(in, -1);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("size  0") : "Exception message should indicate size  0";
        }
    }
}
