/*
 * @test
 * @summary Test GZIPInputStream constructor with invalid and valid headers
 * @run main GZIPInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class GZIPInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testInvalidHeaderThrowsZipException();
        testUnsupportedCompressionMethodThrowsZipException();
        testValidHeaderSucceeds();
    }

    private static void testInvalidHeaderThrowsZipException() {
        // Header with wrong magic number (not 0x1f8b)
        byte[] invalidMagic = new byte[] {
            (byte)0x00, (byte)0x00, // invalid magic bytes
            8, 0, 0, 0, 0, 0, 0, 0
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidMagic);
        try {
            new GZIPInputStream(bais, 512);
            throw new RuntimeException("Expected ZipException for invalid magic header");
        } catch (ZipException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Expected ZipException but got IOException", e);
        }
    }

    private static void testUnsupportedCompressionMethodThrowsZipException() {
        // Header with correct magic number but unsupported compression method (not 8)
        byte[] unsupportedMethod = new byte[] {
            (byte)0x1f, (byte)0x8b, // correct magic
            0x09, // unsupported compression method (valid is 8)
            0, 0, 0, 0, 0, 0, 0
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(unsupportedMethod);
        try {
            new GZIPInputStream(bais, 512);
            throw new RuntimeException("Expected ZipException for unsupported compression method");
        } catch (ZipException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Expected ZipException but got IOException", e);
        }
    }

    private static void testValidHeaderSucceeds() throws IOException {
        // Minimal valid GZIP header with compression method 8
        byte[] validHeader = new byte[] {
            (byte)0x1f, (byte)0x8b, // magic
            8, // compression method
            0, 0, 0, 0, 0, 0, 0
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(validHeader);
        // Should not throw
        GZIPInputStream gis = new GZIPInputStream(bais, 512);
        gis.close();
    }
}
