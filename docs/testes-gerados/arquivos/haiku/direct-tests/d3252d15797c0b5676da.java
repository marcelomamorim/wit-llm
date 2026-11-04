/*
 * @test
 * @summary Verifies OutputStreamWriter(OutputStream, Charset) null handling and encoding of written characters
 * @run main OutputStreamWriterWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testUtf8Encoding();
        testIso88591Encoding();
        testUnmappableReplacement();
        testSurrogatePairUtf8();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] writeAndGet(Charset cs, String s) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStreamWriter w = new OutputStreamWriter(bos, cs);
        w.write(s);
        w.close();
        return bos.toByteArray();
    }

    static void testNullCharsetThrowsNPE() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            new OutputStreamWriter(bos, (Charset) null);
            throw new RuntimeException("NullPointerException expected for null charset");
        } catch (NullPointerException e) {
            check("charset".equals(e.getMessage()),
                  "unexpected NPE message: " + e.getMessage());
        }
    }

    static void testUtf8Encoding() throws IOException {
        byte[] out = writeAndGet(Charset.forName("UTF-8"), "\u00e9");
        check(out.length == 2, "UTF-8 of e-acute must be 2 bytes, got " + out.length);
        check((out[0] & 0xFF) == 0xC3 && (out[1] & 0xFF) == 0xA9,
              "unexpected UTF-8 bytes for e-acute");
    }

    static void testIso88591Encoding() throws IOException {
        byte[] out = writeAndGet(Charset.forName("ISO-8859-1"), "\u00e9");
        check(out.length == 1 && (out[0] & 0xFF) == 0xE9,
              "ISO-8859-1 of e-acute must be single byte 0xE9");
    }

    static void testUnmappableReplacement() throws IOException {
        byte[] out = writeAndGet(Charset.forName("ISO-8859-1"), "\u20AC");
        check(out.length == 1 && out[0] == (byte) '?',
              "unmappable character must be replaced with '?'");
    }

    static void testSurrogatePairUtf8() throws IOException {
        byte[] out = writeAndGet(Charset.forName("UTF-8"), "\uD83D\uDE00");
        check(out.length == 4, "surrogate pair in UTF-8 must be 4 bytes, got " + out.length);
        check((out[0] & 0xFF) == 0xF0 && (out[1] & 0xFF) == 0x9F
              && (out[2] & 0xFF) == 0x98 && (out[3] & 0xFF) == 0x80,
              "unexpected UTF-8 bytes for surrogate pair");
    }
}
