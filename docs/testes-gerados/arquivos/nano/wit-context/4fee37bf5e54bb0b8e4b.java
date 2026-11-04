/* @test    @summary Testa o construtor CharArrayWriter(int initialSize) com valores válidos e inválidos    @run main CharArrayWriterWitupTest */

import java.io.CharArrayWriter;

public class CharArrayWriterWitupTest {

    public static void main(String[] args) {
        testValidInitialSize();
        testNegativeInitialSize();
    }

    private static void testValidInitialSize() {
        try {
            CharArrayWriter writer = new CharArrayWriter(10);
            if (writer == null) {
                throw new AssertionError("Failed to create CharArrayWriter with valid size");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid size: " + e);
        }
    }

    private static void testNegativeInitialSize() {
        try {
            CharArrayWriter writer = new CharArrayWriter(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative size, but none was thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
