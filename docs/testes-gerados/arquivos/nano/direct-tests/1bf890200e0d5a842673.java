/* @test    @summary Test BufferedWriter constructor with a simple Writer instance    @run main BufferedWriterWitupTest */

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.io.Writer;

public class BufferedWriterWitupTest {

    public static void main(String[] args) {
        testBufferedWriterConstructor();
    }

    public static void testBufferedWriterConstructor() {
        StringWriter stringWriter = new StringWriter();
        BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);
        if (bufferedWriter == null) {
            throw new AssertionError("BufferedWriter instance is null");
        }
        // Verify that the BufferedWriter wraps the StringWriter by writing and flushing
        try {
            bufferedWriter.write("test");
            bufferedWriter.flush();
            String result = stringWriter.toString();
            if (!"test".equals(result)) {
                throw new AssertionError("Expected 'test', but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during write/flush: " + e);
        }
    }
}
