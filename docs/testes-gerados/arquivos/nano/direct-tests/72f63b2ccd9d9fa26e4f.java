/* @test    @summary Test ByteArrayOutputStream constructor with valid and invalid sizes    @run main ByteArrayOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidSize();
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
    }

    static void testConstructorWithValidSize() {
        int size = 10;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        assert baos != null : "ByteArrayOutputStream should be instantiated";
        assert baos.buf.length >= size : "Buffer should be at least the specified size";
        assert baos.count == 0 : "Initial count should be zero";
    }

    static void testConstructorWithZeroSize() {
        int size = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        assert baos != null : "ByteArrayOutputStream with zero size should be instantiated";
        assert baos.buf.length >= size : "Buffer should be at least zero";
        assert baos.count == 0 : "Initial count should be zero";
    }

    static void testConstructorWithNegativeSize() {
        int size = -1;
        try {
            new ByteArrayOutputStream(size);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Negative initial size") : "Exception message should mention negative size";
        }
    }
}
