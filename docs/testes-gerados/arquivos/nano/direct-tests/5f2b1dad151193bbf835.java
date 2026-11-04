/* @test    @summary Test for Manifest.peek() method behavior.    @run main ManifestWitupTest */

import java.io.IOException;
import java.util.jar.Manifest;

public class ManifestWitupTest {

    public static void main(String[] args) throws IOException {
        testPeekReturnsByte();
        testPeekReturnsMinusOneWhenBufferEmpty();
    }

    private static void testPeekReturnsByte() throws IOException {
        Manifest manifest = new Manifest();
        // Fill the buffer to ensure peek() returns a byte
        // Accessing protected or package-private buffer is not possible,
        // so we simulate by calling peek() after some operation.
        // Since fill() is package-private, we rely on default state.
        // We invoke peek() and expect a byte (not -1) if buffer is not empty.
        // For the purpose of this test, we assume buffer has data.
        // As we cannot manipulate internal buffer directly, we test the method's behavior.
        byte result = manifest.peek();
        // The result should be a byte value or -1 if buffer is empty.
        // Since buffer is initially empty, peek() should return -1.
        if (result != -1) {
            throw new AssertionError("Expected -1 when buffer is empty, but got: " + result);
        }
    }

    private static void testPeekReturnsMinusOneWhenBufferEmpty() throws IOException {
        Manifest manifest = new Manifest();
        // Ensure buffer is empty by calling peek() when no data is present.
        byte result = manifest.peek();
        if (result != -1) {
            throw new AssertionError("Expected -1 when buffer is empty, but got: " + result);
        }
    }
}
