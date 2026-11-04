/*
 * @test
 * @summary GZIPInputStream(InputStream) accepts a valid GZIP header and rejects invalid headers with ZipException
 * @run main GZIPInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class GZIPInputStreamWitupTest {
    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static void expectZipException(byte[] data, String msg) {
        try {
            GZIPInputStream g = new GZIPInputStream(new ByteArrayInputStream(data));
            g.close();
            check(false, msg + ": no exception thrown");
        } catch (ZipException e) {
            // expected
        } catch (IOException e) {
            check(false, msg + ": expected ZipException but got " + e);
        }
    }

    static void testValidHeader() throws IOException {
        byte[] hdr = {0x1f, (byte) 0x8b, 8, 0, 0, 0, 0, 0, 0, (byte) 0xff};
        GZIPInputStream g = new GZIPInputStream(new ByteArrayInputStream(hdr));
        check(g != null, "valid header should construct a stream");
        g.close();
    }

    static void testBadMagic() {
        byte[] data = {0x00, 0x00, 8, 0, 0, 0, 0, 0, 0, 0};
        expectZipException(data, "bad magic bytes");
    }

    static void testUnsupportedMethod() {
        byte[] data = {0x1f, (byte) 0x8b, 7, 0, 0, 0, 0, 0, 0, 0};
        expectZipException(data, "unsupported compression method");
    }

    static void testNullInput() {
        try {
            new GZIPInputStream(null);
            check(false, "null input should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            check(false, "null input: expected NullPointerException but got " + e);
        }
    }

    public static void main(String[] args) throws Exception {
        testValidHeader();
        testBadMagic();
        testUnsupportedMethod();
        testNullInput();
        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }
}
