/*
 * @test
 * @summary Test CharArrayWriter(int) constructor for valid and invalid initial sizes
 * @run main CharArrayWriterWitupTest
 */

import java.io.CharArrayWriter;

public class CharArrayWriterWitupTest {
    public static void main(String[] args) {
        testValidInitialSize();
        testZeroInitialSize();
        testNegativeInitialSize();
    }

    private static void testValidInitialSize() {
        // Test with a positive initial size
        CharArrayWriter caw = new CharArrayWriter(10);
        if (caw == null) {
            throw new AssertionError("CharArrayWriter instance is null");
        }
    }

    private static void testZeroInitialSize() {
        // Test with zero initial size (allowed)
        CharArrayWriter caw = new CharArrayWriter(0);
        if (caw == null) {
            throw new AssertionError("CharArrayWriter instance is null");
        }
    }

    private static void testNegativeInitialSize() {
        // Test that negative initial size throws IllegalArgumentException
        try {
            new CharArrayWriter(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initial size");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative initial size")) {
                throw new AssertionError("Exception message does not contain expected text: " + msg);
            }
        }
    }
}
