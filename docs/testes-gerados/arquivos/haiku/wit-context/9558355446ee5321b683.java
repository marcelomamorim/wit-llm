/*
 * @test
 * @summary Witup-derived checks for the PrintStream(OutputStream) constructor: null argument rejection and basic observable output
 * @run main PrintStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullOutputThrowsNullPointerException();
        testValidOutputReceivesWrittenData();
        testValidOutputHasNoErrorByDefault();
    }

    static void testNullOutputThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new PrintStream((ByteArrayOutputStream) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        }
    }

    static void testValidOutputReceivesWrittenData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ps.print("abc");
        ps.flush();
        String result = baos.toString();
        if (!"abc".equals(result)) {
            throw new RuntimeException("Unexpected output: " + result);
        }
    }

    static void testValidOutputHasNoErrorByDefault() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ps.print('x');
        ps.flush();
        if (ps.checkError()) {
            throw new RuntimeException("checkError() should be false for a healthy stream");
        }
    }
}
