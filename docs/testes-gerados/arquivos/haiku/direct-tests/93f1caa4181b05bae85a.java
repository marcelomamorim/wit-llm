/*
 * @test
 * @summary Verifies single-byte read() on the Base64 decoder input stream returns decoded bytes, unsigned values, and -1 at end of stream
 * @run main Base64WitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws IOException {
        testDecodesAsciiText();
        testReturnsUnsignedValues();
        testEmptyStreamReturnsMinusOne();
        System.out.println("PASS");
    }

    static void testDecodesAsciiText() throws IOException {
        byte[] encoded = "SGVsbG8=".getBytes(StandardCharsets.US_ASCII);
        byte[] expected = "Hello".getBytes(StandardCharsets.US_ASCII);
        InputStream in = Base64.getDecoder().wrap(new ByteArrayInputStream(encoded));
        for (int i = 0; i < expected.length; i++) {
            int r = in.read();
            if (r != (expected[i] & 0xff)) {
                throw new AssertionError("byte " + i + ": expected " + (expected[i] & 0xff) + " but was " + r);
            }
        }
        int eof = in.read();
        if (eof != -1) {
            throw new AssertionError("expected -1 at end of stream but was " + eof);
        }
    }

    static void testReturnsUnsignedValues() throws IOException {
        // 0xFF 0x80 encodes to "/4A="
        byte[] encoded = "/4A=".getBytes(StandardCharsets.US_ASCII);
        InputStream in = Base64.getDecoder().wrap(new ByteArrayInputStream(encoded));
        int first = in.read();
        if (first != 0xFF) {
            throw new AssertionError("expected 255 but was " + first);
        }
        int second = in.read();
        if (second != 0x80) {
            throw new AssertionError("expected 128 but was " + second);
        }
        if (in.read() != -1) {
            throw new AssertionError("expected -1 at end of stream");
        }
    }

    static void testEmptyStreamReturnsMinusOne() throws IOException {
        InputStream in = Base64.getDecoder().wrap(new ByteArrayInputStream(new byte[0]));
        int r = in.read();
        if (r != -1) {
            throw new AssertionError("expected -1 for empty input but was " + r);
        }
    }
}
