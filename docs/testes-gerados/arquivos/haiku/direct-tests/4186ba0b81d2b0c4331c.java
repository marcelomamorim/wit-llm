/*
 * @test
 * @summary Tests GZIPInputStream constructors: valid header parsing, buffer size validation, and invalid or empty input handling
 * @run main GZIPInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;

public class GZIPInputStreamWitupTest {

    static final byte[] PAYLOAD = "hello gzip constructor test hello gzip constructor test".getBytes();

    public static void main(String[] args) throws Exception {
        testRoundTripWithExplicitSize();
        testRoundTripWithDefaultSize();
        testSmallBufferSize();
        testEmptyPayloadRoundTrip();
        testZeroSizeThrowsIllegalArgument();
        testNegativeSizeThrowsIllegalArgument();
        testNotGzipHeaderThrowsZipException();
        testEmptyInputThrowsIOException();
        System.out.println("PASSED");
    }

    static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (GZIPOutputStream gos = new GZIPOutputStream(bos)) {
            gos.write(data);
        }
        return bos.toByteArray();
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[64];
        int n;
        while ((n = in.read(buf, 0, buf.length)) != -1) {
            out.write(buf, 0, n);
        }
        return out.toByteArray();
    }

    static void testRoundTripWithExplicitSize() throws Exception {
        byte[] gz = compress(PAYLOAD);
        try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(gz), 512)) {
            byte[] actual = readAll(in);
            if (!Arrays.equals(PAYLOAD, actual)) {
                throw new RuntimeException("Round trip mismatch with explicit size");
            }
        }
    }

    static void testRoundTripWithDefaultSize() throws Exception {
        byte[] gz = compress(PAYLOAD);
        try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(gz))) {
            byte[] actual = readAll(in);
            if (!Arrays.equals(PAYLOAD, actual)) {
                throw new RuntimeException("Round trip mismatch with default size");
            }
        }
    }

    static void testSmallBufferSize() throws Exception {
        byte[] gz = compress(PAYLOAD);
        try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(gz), 1)) {
            byte[] actual = readAll(in);
            if (!Arrays.equals(PAYLOAD, actual)) {
                throw new RuntimeException("Round trip mismatch with buffer size 1");
            }
        }
    }

    static void testEmptyPayloadRoundTrip() throws Exception {
        byte[] gz = compress(new byte[0]);
        try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(gz))) {
            byte[] actual = readAll(in);
            if (actual.length != 0) {
                throw new RuntimeException("Expected empty payload, got length " + actual.length);
            }
        }
    }

    static void testZeroSizeThrowsIllegalArgument() throws Exception {
        byte[] gz = compress(PAYLOAD);
        try {
            new GZIPInputStream(new ByteArrayInputStream(gz), 0);
            throw new RuntimeException("Expected IllegalArgumentException for size 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNegativeSizeThrowsIllegalArgument() throws Exception {
        byte[] gz = compress(PAYLOAD);
        try {
            new GZIPInputStream(new ByteArrayInputStream(gz), -1);
            throw new RuntimeException("Expected IllegalArgumentException for size -1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNotGzipHeaderThrowsZipException() throws Exception {
        byte[] bad = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};
        try {
            new GZIPInputStream(new ByteArrayInputStream(bad));
            throw new RuntimeException("Expected ZipException for non-GZIP header");
        } catch (ZipException expected) {
            // expected
        }
    }

    static void testEmptyInputThrowsIOException() throws Exception {
        try {
            new GZIPInputStream(new ByteArrayInputStream(new byte[0]));
            throw new RuntimeException("Expected IOException for empty input");
        } catch (EOFException expected) {
            // expected
        } catch (IOException expected) {
            // also acceptable: header read failure
        }
    }
}
