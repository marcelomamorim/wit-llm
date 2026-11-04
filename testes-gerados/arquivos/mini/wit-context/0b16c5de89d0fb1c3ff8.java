/*
 * @test
 * @summary Test PrintStream(OutputStream out, boolean autoFlush) constructor for null and invalid buffer size
 * @run main PrintStreamWitupTest
 */

import java.io.PrintStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOutputStream();
        testValidConstruction();
        testIllegalArgumentExceptionBufferSize();
    }

    private static void testNullOutputStream() {
        try {
            new PrintStream((OutputStream) null, true);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testValidConstruction() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true);
        if (ps.checkError()) {
            throw new RuntimeException("PrintStream has error after valid construction");
        }
        ps.println("test");
        ps.flush();
        String output = baos.toString("UTF-8");
        if (!output.contains("test")) {
            throw new RuntimeException("PrintStream did not write expected output");
        }
        ps.close();
    }

    private static void testIllegalArgumentExceptionBufferSize() throws Exception {
        // The IllegalArgumentException is expected if defaultCharBufferSize <= 0 and out != null
        // defaultCharBufferSize is a private static final int in PrintStream
        // We use reflection to set it to 0 to trigger the exception
        // This is justified because no public API allows to set this buffer size

        // Save original value
        Field field = PrintStream.class.getDeclaredField("defaultCharBufferSize");
        field.setAccessible(true);
        int originalValue = field.getInt(null);

        try {
            // Set to 0 to trigger IllegalArgumentException
            field.setInt(null, 0);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                new PrintStream(baos, true);
                throw new RuntimeException("Expected IllegalArgumentException due to defaultCharBufferSize <= 0");
            } catch (IllegalArgumentException e) {
                // Expected
            }
        } finally {
            // Restore original value
            field.setInt(null, originalValue);
        }
    }
}
