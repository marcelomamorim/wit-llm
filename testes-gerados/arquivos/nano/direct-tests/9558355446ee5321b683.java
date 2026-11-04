/*
 * @test
 * @summary Test constructor PrintStream(OutputStream out)
 * @run main PrintStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) {
        testPrintStreamConstructorWithByteArrayOutputStream();
    }

    public static void testPrintStreamConstructorWithByteArrayOutputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ps.print("Hello World");
        ps.flush();
        String output = baos.toString();
        if (!"Hello World".equals(output)) {
            throw new AssertionError("Expected 'Hello World' but got: " + output);
        }
    }
}
