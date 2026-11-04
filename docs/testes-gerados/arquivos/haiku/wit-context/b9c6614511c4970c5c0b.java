/*
 * @test
 * @summary Verifies StringWriter(int) rejects negative initial sizes and accepts zero and positive sizes
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeInitialSizeThrows();
        testMinusOneInitialSizeThrows();
        testZeroInitialSizeIsAccepted();
        testPositiveInitialSizeIsAccepted();
    }

    static void testNegativeInitialSizeThrows() {
        try {
            new StringWriter(-1);
            throw new AssertionError("Expected IllegalArgumentException for initialSize=-1");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testMinusOneInitialSizeThrows() {
        try {
            new StringWriter(Integer.MIN_VALUE);
            throw new AssertionError("Expected IllegalArgumentException for Integer.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testZeroInitialSizeIsAccepted() {
        StringWriter sw = new StringWriter(0);
        sw.write("abc");
        String result = sw.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("Unexpected content: " + result);
        }
    }

    static void testPositiveInitialSizeIsAccepted() {
        StringWriter sw = new StringWriter(16);
        sw.write("hello");
        sw.write('!');
        String result = sw.toString();
        if (!"hello!".equals(result)) {
            throw new AssertionError("Unexpected content: " + result);
        }
    }
}
