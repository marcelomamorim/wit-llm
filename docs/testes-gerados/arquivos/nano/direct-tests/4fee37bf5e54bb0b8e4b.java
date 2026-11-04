/* @test    @summary Test CharArrayWriter constructor with valid and invalid initial sizes    @run main CharArrayWriterWitupTest */

import java.io.CharArrayWriter;

public class CharArrayWriterWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidSize();
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
    }

    static void testConstructorWithValidSize() {
        int size = 10;
        CharArrayWriter writer = new CharArrayWriter(size);
        assert writer != null : "Constructor with valid size returned null";
        // Verify internal buffer size by writing and checking no exception
        try {
            writer.write(new char[size], 0, size);
        } catch (Exception e) {
            throw new AssertionError("Writing with buffer of size " + size + " failed", e);
        }
    }

    static void testConstructorWithZeroSize() {
        int size = 0;
        CharArrayWriter writer = new CharArrayWriter(size);
        assert writer != null : "Constructor with zero size returned null";
        // Writing zero characters should not throw
        try {
            writer.write(new char[0], 0, 0);
        } catch (Exception e) {
            throw new AssertionError("Writing zero characters failed", e);
        }
    }

    static void testConstructorWithNegativeSize() {
        int size = -1;
        boolean exceptionThrown = false;
        try {
            new CharArrayWriter(size);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor with negative size did not throw IllegalArgumentException";
    }
}
