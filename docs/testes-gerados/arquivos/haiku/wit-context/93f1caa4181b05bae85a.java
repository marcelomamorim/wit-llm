/*
 * @test
 * @summary Verifies observable read() behavior of the Base64 decoding input stream returned by Base64.getDecoder().wrap()
 * @run main Base64WitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testDecodesSingleBytesThenEof();
        testEmptyInputReturnsEof();
        testIllegalCharacterThrowsIOException();
    }

    static void testDecodesSingleBytesThenEof() throws IOException {
        InputStream in = Base64.getDecoder().wrap(
                new ByteArrayInputStream("QUJD".getBytes("US-ASCII")));
        check(in.read() == 'A', "expected 'A' from first read()");
        check(in.read() == 'B', "expected 'B' from second read()");
        check(in.read() == 'C', "expected 'C' from third read()");
        check(in.read() == -1, "expected EOF (-1) after all decoded bytes");
        check(in.read() == -1, "expected repeated EOF (-1)");
        in.close();
    }

    static void testEmptyInputReturnsEof() throws IOException {
        InputStream in = Base64.getDecoder().wrap(
                new ByteArrayInputStream(new byte[0]));
        check(in.read() == -1, "expected EOF (-1) for empty encoded input");
        in.close();
    }

    static void testIllegalCharacterThrowsIOException() {
        InputStream in = Base64.getDecoder().wrap(
                new ByteArrayInputStream("QU#D".getBytes(java.nio.charset.StandardCharsets.US_ASCII)));
        boolean thrown = false;
        try {
            in.read();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for character outside the base64 alphabet");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
