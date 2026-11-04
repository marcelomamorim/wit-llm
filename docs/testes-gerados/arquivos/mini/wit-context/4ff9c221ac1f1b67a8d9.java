/*
 * @test
 * @summary Test PushbackInputStream constructor with size parameter for valid and invalid sizes
 * @run main PushbackInputStreamWitupTest
 */

import java.io.PushbackInputStream;
import java.io.InputStream;
import java.io.IOException;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testValidSize();
        testInvalidSize();
    }

    private static void testValidSize() throws IOException {
        // Use a simple InputStream that returns EOF immediately
        InputStream emptyStream = new InputStream() {
            public int read() {
                return -1;
            }
        };

        // Test with size = 1 (minimum valid size)
        PushbackInputStream pbis1 = new PushbackInputStream(emptyStream, 1);
        if (pbis1 == null) {
            throw new AssertionError("PushbackInputStream constructor returned null for size=1");
        }

        // Test with size = 10 (arbitrary valid size)
        PushbackInputStream pbis10 = new PushbackInputStream(emptyStream, 10);
        if (pbis10 == null) {
            throw new AssertionError("PushbackInputStream constructor returned null for size=10");
        }
    }

    private static void testInvalidSize() {
        InputStream dummyStream = new InputStream() {
            public int read() {
                return -1;
            }
        };

        // Test with size = 0, expect IllegalArgumentException
        try {
            new PushbackInputStream(dummyStream, 0);
            throw new AssertionError("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test with size = -1, expect IllegalArgumentException
        try {
            new PushbackInputStream(dummyStream, -1);
            throw new AssertionError("Expected IllegalArgumentException for size=-1");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
