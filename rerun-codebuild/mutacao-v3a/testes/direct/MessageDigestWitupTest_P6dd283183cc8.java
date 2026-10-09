/* @test    @summary Test MessageDigest.digest(byte[] buf, int offset, int len) method behavior    @run main MessageDigestWitupTest_P6dd283183cc8 */

import java.security.*;
import java.util.*;

public class MessageDigestWitupTest_P6dd283183cc8 {
    public static void main(String[] args) throws Exception {
        testDigestWithValidBuffer();
        testDigestWithNullBuffer();
        testDigestWithInsufficientBuffer();
        testDigestReturnsExpectedBytesCount();
    }

    private static void testDigestWithValidBuffer() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] input = {1, 2, 3, 4, 5};
        byte[] output = new byte[64]; // larger than digest size
        int result = md.digest(output, 0, 64);
        if (result <= 0 || result > output.length) {
            throw new AssertionError("Unexpected digest length: " + result);
        }
        // Optionally, verify digest length matches algorithm's output size
        int expectedLength = md.getDigestLength();
        if (result != expectedLength) {
            throw new AssertionError("Digest length mismatch: expected " + expectedLength + " but got " + result);
        }
    }

    private static void testDigestWithNullBuffer() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.digest(null, 0, 10);
            throw new AssertionError("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (DigestException e) {
            throw new AssertionError("Unexpected DigestException", e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }

    private static void testDigestWithInsufficientBuffer() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] smallBuffer = new byte[5];
            md.digest(smallBuffer, 0, 10);
            throw new AssertionError("Expected IllegalArgumentException for insufficient buffer size");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (DigestException e) {
            throw new AssertionError("Unexpected DigestException", e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }

    private static void testDigestReturnsExpectedBytesCount() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] input = {10, 20, 30, 40, 50};
        byte[] buffer = new byte[32]; // SHA-256 digest size
        int bytesWritten = md.digest(buffer, 0, buffer.length);
        if (bytesWritten != md.getDigestLength()) {
            throw new AssertionError("Returned byte count does not match digest length: " + bytesWritten);
        }
    }
}
