/*
 * @test
 * @summary Verifies BufferedWriter(Writer, int) rejects non-positive buffer sizes and produces correct output for small and default buffers
 * @run main BufferedWriterWitupTest
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

public class BufferedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        checkNonPositiveSizeRejected(0);
        checkNonPositiveSizeRejected(-1);
        checkNonPositiveSizeRejected(Integer.MIN_VALUE);
        checkSmallBufferOutput();
        checkWriteSingleCharAndFlush();
        checkDefaultConstructorOutput();
        checkPositiveSizeAccepted();
    }

    static void checkNonPositiveSizeRejected(int sz) {
        StringWriter sw = new StringWriter();
        boolean thrown = false;
        try {
            new BufferedWriter(sw, sz);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for sz=" + sz);
        }
    }

    static void checkSmallBufferOutput() throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw, 2);
        bw.write("abcdefg");
        bw.flush();
        check("abcdefg", sw.toString());
    }

    static void checkWriteSingleCharAndFlush() throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw, 8);
        bw.write('x');
        bw.write('y');
        bw.flush();
        check("xy", sw.toString());
    }

    static void checkDefaultConstructorOutput() throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write("hello");
        bw.flush();
        check("hello", sw.toString());
    }

    static void checkPositiveSizeAccepted() throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw, 1);
        bw.write("z");
        bw.flush();
        check("z", sw.toString());
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }
}
