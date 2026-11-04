/*
 * @test
 * @summary InputStreamReader(InputStream, Charset) rejects a null charset and decodes bytes using the given charset
 * @run main InputStreamReaderWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InputStreamReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testIso88591Decoding();
        testUtf8MultiByteDecoding();
        testBulkReadAscii();
    }

    static void testNullCharsetThrowsNPE() {
        try {
            new InputStreamReader(new ByteArrayInputStream(new byte[0]), (Charset) null);
            throw new RuntimeException("expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testIso88591Decoding() throws IOException {
        byte[] data = { (byte) 0xE9 };
        try (InputStreamReader r = new InputStreamReader(
                new ByteArrayInputStream(data), Charset.forName("ISO-8859-1"))) {
            check(r.read() == (char) 0xE9, "ISO-8859-1 byte 0xE9 must decode to U+00E9");
            check(r.read() == -1, "stream must be at EOF after one char");
        }
    }

    static void testUtf8MultiByteDecoding() throws IOException {
        byte[] data = { (byte) 0xC3, (byte) 0xA9 };
        try (InputStreamReader r = new InputStreamReader(
                new ByteArrayInputStream(data), Charset.forName("UTF-8"))) {
            check(r.read() == (char) 0xE9, "UTF-8 bytes C3 A9 must decode to U+00E9");
            check(r.read() == -1, "stream must be at EOF after one char");
        }
    }

    static void testBulkReadAscii() throws IOException {
        byte[] data = "abc".getBytes("US-ASCII");
        StringBuilder sb = new StringBuilder();
        try (InputStreamReader r = new InputStreamReader(
                new ByteArrayInputStream(data), Charset.forName("UTF-8"))) {
            char[] buf = new char[2];
            int n;
            while ((n = r.read(buf, 0, buf.length)) != -1) {
                sb.append(buf, 0, n);
            }
        }
        check(sb.toString().equals("abc"), "decoded text must be 'abc' but was '" + sb + "'");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
