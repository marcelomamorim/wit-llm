/*
 * @test
 * @summary Null-argument and basic observable behavior of Base64.Encoder.wrap
 * @run main Base64WitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testNullStreamThrowsNpe();
        testWrapEncodesHello();
        testWrapEmptyInput();
    }

    static void testNullStreamThrowsNpe() {
        try {
            Base64.getEncoder().wrap(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null stream");
    }

    static void testWrapEncodesHello() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (OutputStream os = Base64.getEncoder().wrap(bos)) {
            os.write("Hello".getBytes(StandardCharsets.US_ASCII));
        }
        String out = new String(bos.toByteArray(), StandardCharsets.US_ASCII);
        if (!"SGVsbG8=".equals(out)) {
            throw new AssertionError("unexpected encoding: " + out);
        }
    }

    static void testWrapEmptyInput() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (OutputStream os = Base64.getEncoder().wrap(bos)) {
            // no data written
        }
        if (bos.size() != 0) {
            throw new AssertionError("expected empty output, got size " + bos.size());
        }
    }
}
