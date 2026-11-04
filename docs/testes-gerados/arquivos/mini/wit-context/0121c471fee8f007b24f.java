/*
 * @test
 * @summary Test Base64.wrap(OutputStream) method for null input and normal wrapping
 * @run main Base64WitupTest
 */

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class Base64WitupTest {
    public static void main(String[] args) throws IOException {
        testWrapNullOutputStream();
        testWrapValidOutputStream();
    }

    private static void testWrapNullOutputStream() {
        try {
            Base64.wrap(null);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testWrapValidOutputStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream wrapped = Base64.wrap(baos);
        if (wrapped == null) {
            throw new RuntimeException("wrap() returned null OutputStream");
        }
        // Write some bytes and flush to check basic functionality
        byte[] input = "test".getBytes("UTF-8");
        wrapped.write(input);
        wrapped.flush();
        // The underlying baos should have some data (Base64 encoded)
        byte[] encoded = baos.toByteArray();
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
        // Basic check: encoded output should not be equal to input
        boolean same = true;
        if (encoded.length == input.length) {
            for (int i = 0; i < input.length; i++) {
                if (encoded[i] != input[i]) {
                    same = false;
                    break;
                }
            }
        } else {
            same = false;
        }
        if (same) {
            throw new RuntimeException("Encoded output should differ from input bytes");
        }
        wrapped.close();
    }
}
