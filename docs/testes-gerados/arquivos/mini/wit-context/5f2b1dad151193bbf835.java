/*
 * @test
 * @summary Test java.util.jar.Manifest.peek() method for expected behavior and edge cases
 * @run main ManifestWitupTest
 */

import java.util.jar.Manifest;
import java.io.IOException;

public class ManifestWitupTest {
    public static void main(String[] args) throws IOException {
        testPeekReturnsMinusOneWhenBufferEmpty();
        testPeekReturnsByteWhenBufferNotEmpty();
    }

    private static void testPeekReturnsMinusOneWhenBufferEmpty() throws IOException {
        // Create a Manifest.FastInputStream with empty buffer by subclassing Manifest
        Manifest.FastInputStream fis = new Manifest.FastInputStream(new java.io.ByteArrayInputStream(new byte[0]));
        int result = fis.peek();
        if (result != -1) {
            throw new AssertionError("peek() should return -1 when buffer is empty, got: " + result);
        }
    }

    private static void testPeekReturnsByteWhenBufferNotEmpty() throws IOException {
        byte[] data = {42};
        Manifest.FastInputStream fis = new Manifest.FastInputStream(new java.io.ByteArrayInputStream(data));
        int firstPeek = fis.peek();
        if (firstPeek != 42) {
            throw new AssertionError("peek() should return first byte 42, got: " + firstPeek);
        }
        int secondPeek = fis.peek();
        if (secondPeek != 42) {
            throw new AssertionError("peek() should return same byte 42 on repeated calls, got: " + secondPeek);
        }
    }
}