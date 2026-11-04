/* @test    @summary Test constructor PrintStream(OutputStream out, boolean autoFlush) for correct behavior and null handling    @run main PrintStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullOutputStream();
    }

    static void testValidConstructor() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true);
        ps.println("test");
        String output = baos.toString();
        if (!output.contains("test")) {
            throw new AssertionError("Expected output to contain 'test', but got: " + output);
        }
    }

    static void testNullOutputStream() {
        boolean exceptionThrown = false;
        try {
            new PrintStream((OutputStream) null, true);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null OutputStream");
        }
    }
}
