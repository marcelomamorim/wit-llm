/*
 * @test
 * @summary Witup-derived checks for Base64.Decoder.decode(byte[], byte[]) output buffer size validation
 * @run main Base64WitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testBasicDecoderOutputTooSmall();
        testUrlDecoderOutputTooSmall();
        testBasicDecoderSufficientOutput();
        testUrlDecoderSufficientOutput();
        testBasicDecoderEmptyInput();
    }

    static void testBasicDecoderOutputTooSmall() {
        Base64.Decoder dec = Base64.getDecoder();
        byte[] src = "QUJD".getBytes(StandardCharsets.US_ASCII);
        expectIllegalArgument(dec, src, new byte[2]);
    }

    static void testUrlDecoderOutputTooSmall() {
        Base64.Decoder dec = Base64.getUrlDecoder();
        byte[] src = "QUJD".getBytes(StandardCharsets.US_ASCII);
        expectIllegalArgument(dec, src, new byte[2]);
    }

    static void testBasicDecoderSufficientOutput() {
        Base64.Decoder dec = Base64.getDecoder();
        byte[] src = "QUJD".getBytes(StandardCharsets.US_ASCII);
        byte[] dst = new byte[3];
        int n = dec.decode(src, dst);
        check(n == 3, "expected 3 bytes decoded, got " + n);
        check(dst[0] == 'A' && dst[1] == 'B' && dst[2] == 'C', "unexpected decoded content");
    }

    static void testUrlDecoderSufficientOutput() {
        Base64.Decoder dec = Base64.getUrlDecoder();
        byte[] src = "QUJD".getBytes(StandardCharsets.US_ASCII);
        byte[] dst = new byte[10];
        int n = dec.decode(src, dst);
        check(n == 3, "expected 3 bytes decoded, got " + n);
        check(dst[0] == 'A' && dst[1] == 'B' && dst[2] == 'C', "unexpected decoded content");
    }

    static void testBasicDecoderEmptyInput() {
        Base64.Decoder dec = Base64.getDecoder();
        int n = dec.decode(new byte[0], new byte[0]);
        check(n == 0, "expected 0 bytes decoded for empty input, got " + n);
    }

    static void expectIllegalArgument(Base64.Decoder dec, byte[] src, byte[] dst) {
        try {
            dec.decode(src, dst);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
