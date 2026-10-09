/*
 * @test
 * @summary Test StringWriter.write(String str, int off, int len) method for correct substring writing.
 * @run main StringWriterWitupTest_P7a00f3429ab9
 */

import java.io.StringWriter;

public class StringWriterWitupTest_P7a00f3429ab9 {
    public static void main(String[] args) {
        testWriteSubstring()
        ;
    }

    public static void testWriteSubstring() {
        StringWriter writer = new StringWriter();
        String testStr = "Hello, World!";
        int off = 7;
        int len = 5;
        writer.write(testStr, off, len);
        String result = writer.toString();
        if (!"World".equals(result)) {
            throw new AssertionError("Expected 'World', but got: " + result);
        }
    }
}
