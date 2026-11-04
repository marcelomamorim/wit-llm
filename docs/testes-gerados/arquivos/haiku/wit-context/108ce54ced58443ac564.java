/*
 * @test
 * @summary Checks public behavior of Base64.getMimeEncoder(int, byte[]) for null separators, illegal separator characters, MIME line breaks and non-positive rounded line lengths
 * @run main Base64WitupTest
 */

import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testNullLineSeparatorThrowsNPE();
        testBase64AlphabetSeparatorThrowsIAE();
        testMimeLineBreaksWithCrLf();
        testRoundedLineLengthNotPositiveBehavesAsBasic();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] sampleData() {
        byte[] data = new byte[100];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        return data;
    }

    static void testNullLineSeparatorThrowsNPE() {
        try {
            Base64.getMimeEncoder(76, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null lineSeparator");
    }

    static void testBase64AlphabetSeparatorThrowsIAE() {
        byte[][] separators = { { 'A' }, { '\r', 'a' }, { '+' } };
        for (byte[] sep : separators) {
            boolean thrown = false;
            try {
                Base64.getMimeEncoder(76, sep);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "expected IllegalArgumentException for base64 alphabet separator");
        }
    }

    static void testMimeLineBreaksWithCrLf() {
        byte[] crlf = { '\r', '\n' };
        byte[] data = sampleData();
        String plain = Base64.getEncoder().encodeToString(data);
        String mime = Base64.getMimeEncoder(76, crlf).encodeToString(data);
        check(mime.length() == plain.length() + 2, "unexpected MIME output length");
        check(mime.indexOf("\r\n") == 76, "first line break should be after 76 characters");
        check(mime.replace("\r\n", "").equals(plain), "MIME output without separators must equal basic output");
    }

    static void testRoundedLineLengthNotPositiveBehavesAsBasic() {
        byte[] crlf = { '\r', '\n' };
        byte[] data = sampleData();
        String plain = Base64.getEncoder().encodeToString(data);
        check(Base64.getMimeEncoder(0, crlf).encodeToString(data).equals(plain),
              "lineLength 0 should produce basic encoding");
        check(Base64.getMimeEncoder(3, crlf).encodeToString(data).equals(plain),
              "lineLength 3 rounds to 0 and should produce basic encoding");
    }
}
