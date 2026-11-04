/*
 * @test
 * @summary Test CharArrayWriter(int initialSize) constructor behavior
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
        CharArrayWriter caw = new CharArrayWriter(10);
        if (caw == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }

    private static void testZeroInitialSize() {
        CharArrayWriter caw = new CharArrayWriter(0);
        if (caw == null) {
            throw new RuntimeException("Constructor returned null for zero size");
        }
    }

    private static void testNegativeInitialSize() {
        try {
            new CharArrayWriter(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative initial size");
        } catch (IllegalArgumentException iae) {
            String msg = iae.getMessage();
            if (msg == null || !msg.contains("Negative initial size")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}
