/*
 * @test
 * @summary Test StringWriter.write(String, int, int) method
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {
    public static void main(String[] args) {
        testWriteSubstring();
        testWriteEmptySubstring();
        testWriteFullString();
        testWriteWithOffsetZeroLenZero();
    }

    private static void testWriteSubstring() {
        StringWriter sw = new StringWriter();
        sw.write("HelloWorld", 5, 5); // should append "World"
        String result = sw.toString();
        if (!"World".equals(result)) {
            throw new RuntimeException("testWriteSubstring failed: expected 'World' but got '" + result + "'");
        }
    }

    private static void testWriteEmptySubstring() {
        StringWriter sw = new StringWriter();
        sw.write("HelloWorld", 0, 0); // should append empty string
        String result = sw.toString();
        if (!"".equals(result)) {
            throw new RuntimeException("testWriteEmptySubstring failed: expected empty string but got '" + result + "'");
        }
    }

    private static void testWriteFullString() {
        StringWriter sw = new StringWriter();
        sw.write("HelloWorld", 0, 10); // should append "HelloWorld"
        String result = sw.toString();
        if (!"HelloWorld".equals(result)) {
            throw new RuntimeException("testWriteFullString failed: expected 'HelloWorld' but got '" + result + "'");
        }
    }

    private static void testWriteWithOffsetZeroLenZero() {
        StringWriter sw = new StringWriter();
        sw.write("", 0, 0); // empty string, zero offset and length
        String result = sw.toString();
        if (!"".equals(result)) {
            throw new RuntimeException("testWriteWithOffsetZeroLenZero failed: expected empty string but got '" + result + "'");
        }
    }
}
