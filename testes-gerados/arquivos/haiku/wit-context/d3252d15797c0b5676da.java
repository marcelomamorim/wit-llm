/*
 * @test
 * @summary Witup-derived checks for OutputStreamWriter(OutputStream, Charset): null charset rejection and basic UTF-8 encoding
 * @run main OutputStreamWriterWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testUtf8EncodingOfAsciiAndLatin1();
        testNonNullCharsetWritesEncodedBytes();
    }

    static void testNullCharsetThrowsNPE() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Charset cs = null;
        boolean thrown = false;
        try {
            new OutputStreamWriter(out, cs);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null Charset");
        }
    }

    static void testUtf8EncodingOfAsciiAndLatin1() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStreamWriter w = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        w.write("a\u00e9");
        w.flush();
        w.close();
        byte[] expected = {0x61, (byte) 0xC3, (byte) 0xA9};
        check(expected, out.toByteArray(), "UTF-8 encoding of a\u00e9");
    }

    static void testNonNullCharsetWritesEncodedBytes() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStreamWriter w = new OutputStreamWriter(out, StandardCharsets.US_ASCII);
        w.write("abc");
        w.flush();
        w.close();
        byte[] expected = {0x61, 0x62, 0x63};
        check(expected, out.toByteArray(), "US-ASCII encoding of abc");
    }

    static void check(byte[] expected, byte[] actual, String what) {
        if (expected.length != actual.length) {
            throw new RuntimeException(what + ": length mismatch, expected "
                    + expected.length + " but was " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                throw new RuntimeException(what + ": byte mismatch at index " + i);
            }
        }
    }
}
