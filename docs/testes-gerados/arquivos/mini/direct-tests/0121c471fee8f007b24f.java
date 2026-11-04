/*
 * @test
 * @summary Test Base64.wrap(OutputStream) method behavior
 * @run main Base64WitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testWrapWithNullOutputStream();
        testWrapWithValidOutputStream();
    }

    private static void testWrapWithNullOutputStream() {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            encoder.wrap(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testWrapWithValidOutputStream() throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream wrapped = encoder.wrap(baos);
        if (wrapped == null) {
            throw new RuntimeException("wrap() returned null OutputStream");
        }
        // Write known bytes and flush
        byte[] input = "test".getBytes("UTF-8");
        wrapped.write(input);
        wrapped.close();
        // The output should be the Base64 encoding of "test"
        String encoded = baos.toString("UTF-8");
        if (!encoded.equals("dGVzdA==")) {
            throw new RuntimeException("Unexpected Base64 encoding: " + encoded);
        }
    }
}
