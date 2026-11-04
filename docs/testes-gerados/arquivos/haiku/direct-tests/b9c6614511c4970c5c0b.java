/*
 * @test
 * @summary StringWriter(int initialSize) rejects negative sizes and accepts zero or positive sizes with correct observable content
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeOneThrowsIllegalArgument();
        testNegativeMinValueThrowsIllegalArgument();
        testZeroSizeIsUsable();
        testPositiveSizeWritesAreObservable();
    }

    static void testNegativeOneThrowsIllegalArgument() {
        try {
            new StringWriter(-1);
            throw new RuntimeException("Expected IllegalArgumentException for initialSize=-1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNegativeMinValueThrowsIllegalArgument() {
        try {
            new StringWriter(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testZeroSizeIsUsable() {
        StringWriter w = new StringWriter(0);
        w.write("abc");
        String result = w.toString();
        if (!"abc".equals(result)) {
            throw new RuntimeException("Expected \"abc\" but got \"" + result + "\"");
        }
    }

    static void testPositiveSizeWritesAreObservable() {
        StringWriter w = new StringWriter(16);
        w.write('x');
        w.write("yz");
        char[] data = {'a', 'b', 'c'};
        w.write(data, 1, 2);
        String result = w.toString();
        if (!"xyzbc".equals(result)) {
            throw new RuntimeException("Expected \"xyzbc\" but got \"" + result + "\"");
        }
    }
}
