/* @test    @summary Test StringWriter constructor with valid initialSize    @run main StringWriterWitupTest */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) {
        testValidInitialSize();
        testNegativeInitialSize();
    }

    static void testValidInitialSize() {
        int size = 10;
        StringWriter sw = new StringWriter(size);
        assert sw != null : "StringWriter should not be null";
        StringWriter sw2 = new StringWriter(0);
        assert sw2 != null : "StringWriter with zero size should not be null";
    }

    static void testNegativeInitialSize() {
        boolean exceptionThrown = false;
        try {
            new StringWriter(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for negative initialSize";
    }
}
