/*
 * @test
 * @summary Verifies that PrintStream(OutputStream) forwards printed data to the wrapped stream without an explicit flush and does not set the error flag for normal output.
 * @run main PrintStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class PrintStreamWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testPrintStringReachesUnderlyingStream() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        ps.print("abc");
        check(Arrays.equals(bos.toByteArray(), "abc".getBytes()),
              "print(String) data not forwarded to wrapped stream");
        check(!ps.checkError(), "checkError should be false after normal print");
    }

    private static void testPrintlnWritesLineSeparator() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        ps.println("x");
        String expected = "x" + System.lineSeparator();
        check(Arrays.equals(bos.toByteArray(), expected.getBytes()),
              "println output mismatch");
        check(!ps.checkError(), "checkError should be false after println");
    }

    private static void testWriteIntForwardsByte() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        ps.write(65);
        check(bos.size() == 1 && bos.toByteArray()[0] == 65,
              "write(int) did not forward byte 65");
    }

    private static void testPrintIntegerOutput() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        ps.print(42);
        check(Arrays.equals(bos.toByteArray(), "42".getBytes()),
              "print(int) output mismatch");
    }

    public static void main(String[] args) throws Exception {
        testPrintStringReachesUnderlyingStream();
        testPrintlnWritesLineSeparator();
        testWriteIntForwardsByte();
        testPrintIntegerOutput();
    }
}
