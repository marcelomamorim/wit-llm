/*
 * @test
 * @summary Tests for GZIPInputStream constructor with InputStream and size, focusing on exception paths.
 * @run main GZIPInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.GZIPInputStream;

public class GZIPInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithInvalidHeader();
        testConstructorWithInvalidCompressionMethod();
    }

    private static void testConstructorWithInvalidHeader() {
        byte[] invalidHeader = new byte[] {0x00, 0x00, 0x00, 0x00};
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidHeader);
        try {
            new GZIPInputStream(bais, 512);
            System.err.println("Expected ZipException for invalid header, but none was thrown.");
        } catch (ZipException e) {
            System.out.println("Caught expected ZipException for invalid header.");
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        }
    }

    private static void testConstructorWithInvalidCompressionMethod() {
        byte[] invalidMethodHeader = new byte[] {0x1f, (byte)0x8b, 0x00, 0x00};
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidMethodHeader);
        try {
            new GZIPInputStream(bais, 512);
            System.err.println("Expected ZipException for invalid compression method, but none was thrown.");
        } catch (ZipException e) {
            System.out.println("Caught expected ZipException for invalid compression method.");
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        }
    }
}
