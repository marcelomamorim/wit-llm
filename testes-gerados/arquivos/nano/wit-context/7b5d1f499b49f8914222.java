/*
 * @test
 * @summary Test for StringWriter.write(char[], int, int) method, focusing on exception paths.
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) {
        testWriteWithInvalidParameters();
        testWriteWithZeroLength();
        testWriteWithValidParameters();
    }

    static void testWriteWithInvalidParameters() {
        StringWriter sw = new StringWriter();
        char[] buffer = {'a', 'b', 'c'};
        try {
            sw.write(buffer, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        try {
            sw.write(buffer, 4, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset beyond array length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        try {
            sw.write(buffer, 1, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        try {
            sw.write(buffer, 2, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset + length beyond array length");
        } catch (IndexOutOfBoundsException e) {
            // Expected

        }
    }

    static void testWriteWithZeroLength() {
        StringWriter sw = new StringWriter();
        char[] buffer = {'x', 'y', 'z'};
        sw.write(buffer, 0, 0); // Should not throw
        sw.write("hello", 0, 0); // Should not throw
        String result = sw.toString();
        if (!result.isEmpty()) {
            throw new AssertionError("Expected empty string after zero-length writes");
        }
    }

    static void testWriteWithValidParameters() {
        StringWriter sw = new StringWriter();
        char[] buffer = {'a', 'b', 'c', 'd'};
        sw.write(buffer, 1, 2); // writes 'b', 'c'
        String result = sw.toString();
        if (!"bc".equals(result)) {
            throw new AssertionError("Unexpected content: " + result);
        }
        sw.write("xyz", 1, 2); // writes 'y', 'z'
        result = sw.toString();
        if (!"bcyz".equals(result)) {
            throw new AssertionError("Unexpected content after string write: " + result);
        }
    }
}
