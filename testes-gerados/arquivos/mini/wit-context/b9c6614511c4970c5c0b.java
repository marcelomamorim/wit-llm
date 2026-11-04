/*
 * @test
 * @summary Test StringWriter(int initialSize) constructor for valid and invalid inputs
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {
    public static void main(String[] args) {
        testValidInitialSize();
        testNegativeInitialSizeThrows();
        System.out.println("StringWriterWitupTest passed.");
    }

    private static void testValidInitialSize() {
        // Test with zero initial size
        StringWriter sw0 = new StringWriter(0);
        sw0.write('a');
        if (!"a".equals(sw0.toString())) {
            throw new AssertionError("Expected 'a' after writing to StringWriter with initialSize 0");
        }

        // Test with positive initial size
        StringWriter sw10 = new StringWriter(10);
        sw10.write('b');
        if (!"b".equals(sw10.toString())) {
            throw new AssertionError("Expected 'b' after writing to StringWriter with initialSize 10");
        }
    }

    private static void testNegativeInitialSizeThrows() {
        try {
            new StringWriter(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initialSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative buffer size")) {
                throw new AssertionError("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}
