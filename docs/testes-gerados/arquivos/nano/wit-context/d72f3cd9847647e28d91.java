/*
 * @test
 * @summary Test GZIPInputStream constructor with InputStream
 * @run main GZIPInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.GZIPInputStream;

public class GZIPInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidInput();
        testConstructorWithNullInput();
        testConstructorWithInvalidHeader();
    }

    private static void testConstructorWithValidInput() {
        byte[] validGzipHeader = { (byte)0x1f, (byte)0x8b, 8, 0, 0, 0, 0, 0, 0, 0 };
        ByteArrayInputStream bais = new ByteArrayInputStream(validGzipHeader);
        try {
            GZIPInputStream gis = new GZIPInputStream(bais);
            // No exception expected
            gis.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception in valid input test", e);
        }
    }

    private static void testConstructorWithNullInput() {
        try {
            GZIPInputStream gis = new GZIPInputStream((ByteArrayInputStream) null);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for null InputStream", e);
        }
    }

    private static void testConstructorWithInvalidHeader() {
        byte[] invalidHeader = { (byte)0x00, (byte)0x00, 0, 0, 0, 0, 0, 0, 0, 0 };
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidHeader);
        try {
            GZIPInputStream gis = new GZIPInputStream(bais);
            // Reading should throw ZipException due to invalid header
            gis.read();
            gis.close();
            throw new AssertionError("Expected ZipException for invalid header");
        } catch (ZipException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in invalid header test", e);
        }
    }
}
