/*
 * @test
 * @summary Verifies GZIPInputStream(InputStream, int) header validation and buffer size checks
 * @run main GZIPInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class GZIPInputStreamWitupTest {

    static final byte[] VALID_EMPTY_GZIP = {
        0x1f, (byte) 0x8b, 8, 0, 0, 0, 0, 0, 0, (byte) 0xff,
        3, 0,
        0, 0, 0, 0,
        0, 0, 0, 0
    };

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectZipException(byte[] data, String message) throws Exception {
        boolean thrown = false;
        try {
            new GZIPInputStream(new ByteArrayInputStream(data));
        } catch (ZipException e) {
            thrown = true;
        }
        check(thrown, message);
    }

    static void testBadMagic() throws Exception {
        byte[] data = {0x1f, 0x00, 8, 0, 0, 0, 0, 0, 0, (byte) 0xff};
        expectZipException(data, "bad magic must raise ZipException");
    }

    static void testUnsupportedCompressionMethod() throws Exception {
        byte[] data = {0x1f, (byte) 0x8b, 9, 0, 0, 0, 0, 0, 0, (byte) 0xff};
        expectZipException(data, "unsupported compression method must raise ZipException");
    }

    static void testEmptyInputRaisesEOF() throws Exception {
        boolean thrown = false;
        try {
            new GZIPInputStream(new ByteArrayInputStream(new byte[0]));
        } catch (EOFException e) {
            thrown = true;
        }
        check(thrown, "empty input must raise EOFException");
    }

    static void testNonPositiveSizeRejected() throws Exception {
        boolean thrown = false;
        try {
            new GZIPInputStream(new ByteArrayInputStream(VALID_EMPTY_GZIP), 0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "size 0 must raise IllegalArgumentException");
    }

    static void testValidHeaderAccepted() throws Exception {
        GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(VALID_EMPTY_GZIP), 16);
        check(in.read() == -1, "empty gzip member must yield end of stream");
        in.close();
    }

    public static void main(String[] args) throws Exception {
        testBadMagic();
        testUnsupportedCompressionMethod();
        testEmptyInputRaisesEOF();
        testNonPositiveSizeRejected();
        testValidHeaderAccepted();
    }
}
