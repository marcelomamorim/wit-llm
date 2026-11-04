/*
 * @test
 * @summary Verify InputStreamReader(InputStream, Charset) rejects a null charset and decodes bytes with the given charset
 * @run main InputStreamReaderWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class InputStreamReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testUtf8Decoding();
        testIso88591Decoding();
    }

    static void testNullCharsetThrowsNPE() {
        try {
            new InputStreamReader(new ByteArrayInputStream(new byte[0]), (Charset) null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            if (!"charset".equals(e.getMessage())) {
                throw new AssertionError("Unexpected NPE message: " + e.getMessage());
            }
        }
    }

    static void testUtf8Decoding() throws Exception {
        byte[] data = {0x68, (byte) 0xC3, (byte) 0xA9};
        InputStreamReader r = new InputStreamReader(
                new ByteArrayInputStream(data), StandardCharsets.UTF_8);
        char[] buf = new char[10];
        int n = r.read(buf, 0, buf.length);
        r.close();
        if (n != 2 || buf[0] != 'h' || buf[1] != '\u00e9') {
            throw new AssertionError("UTF-8 decoding failed, n=" + n);
        }
    }

    static void testIso88591Decoding() throws Exception {
        byte[] data = {(byte) 0xE9};
        InputStreamReader r = new InputStreamReader(
                new ByteArrayInputStream(data), StandardCharsets.ISO_8859_1);
        int c = r.read();
        r.close();
        if (c != '\u00e9') {
            throw new AssertionError("ISO-8859-1 decoding failed, c=" + c);
        }
    }
}
