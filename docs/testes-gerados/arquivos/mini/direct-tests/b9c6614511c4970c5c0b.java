/*
 * @test
 * @summary Test StringWriter(int initialSize) constructor behavior
 * @run main StringWriterWitupTest
 */

public class StringWriterWitupTest {
    public static void main(String[] args) {
        testNegativeInitialSize();
        testZeroInitialSize();
        testPositiveInitialSize();
    }

    private static void testNegativeInitialSize() {
        try {
            new java.io.StringWriter(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative initialSize");
        } catch (IllegalArgumentException e) {
            if (!"Negative buffer size".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testZeroInitialSize() {
        java.io.StringWriter sw = new java.io.StringWriter(0);
        sw.write('a');
        String result = sw.toString();
        if (!"a".equals(result)) {
            throw new RuntimeException("Expected 'a' but got: " + result);
        }
    }

    private static void testPositiveInitialSize() {
        java.io.StringWriter sw = new java.io.StringWriter(10);
        sw.write('b');
        String result = sw.toString();
        if (!"b".equals(result)) {
            throw new RuntimeException("Expected 'b' but got: " + result);
        }
    }
}
