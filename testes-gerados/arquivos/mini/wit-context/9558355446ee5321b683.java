/*
 * @test
 * @summary Test java.io.PrintStream(OutputStream out) constructor for null and valid OutputStream
 * @run main PrintStreamWitupTest
 */

import java.io.PrintStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

public class PrintStreamWitupTest {
    public static void main(String[] args) {
        testNullOutputStream();
        testValidOutputStream();
    }

    private static void testNullOutputStream() {
        try {
            new PrintStream((OutputStream) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testValidOutputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        if (ps.checkError()) {
            throw new RuntimeException("PrintStream has error after construction");
        }
        ps.print("test");
        ps.flush();
        String output = baos.toString();
        if (!"test".equals(output)) {
            throw new RuntimeException("PrintStream output mismatch: expected 'test' but got '" + output + "'");
        }
    }
}
