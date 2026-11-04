/*
 * @test
 * @summary Base64 Decoder.wrap null check and decoding behavior through public API
 * @run main Base64WitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws IOException {
        testNullArgumentThrowsNPE();
        testBasicDecoderWrap();
        testMimeDecoderWrapIgnoresLineSeparators();
    }

    static void testNullArgumentThrowsNPE() {
        try {
            Base64.getDecoder().wrap(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected");
    }

    static void testBasicDecoderWrap() throws IOException {
        InputStream in = Base64.getDecoder().wrap(
                new ByteArrayInputStream("SGVsbG8=".getBytes(StandardCharsets.US_ASCII)));
        check("Hello", readAll(in));
    }

    static void testMimeDecoderWrapIgnoresLineSeparators() throws IOException {
        byte[] src = "SGVs\r\nbG8=".getBytes(StandardCharsets.US_ASCII);
        InputStream in = Base64.getMimeDecoder().wrap(new ByteArrayInputStream(src));
        check("Hello", readAll(in));
    }

    static String readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[16];
        int n;
        while ((n = in.read(buf)) != -1) {
            out.write(buf, 0, n);
        }
        return new String(out.toByteArray(), StandardCharsets.US_ASCII);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }
}
