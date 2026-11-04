/*
 * @test
 * @summary Verify BufferedWriter(Writer, int) rejects non-positive buffer sizes and buffers output correctly
 * @run main BufferedWriterWitupTest
 */

import java.io.BufferedWriter;
import java.io.StringWriter;

public class BufferedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        checkRejected(0);
        checkRejected(-1);
        checkRejected(Integer.MIN_VALUE);

        StringWriter sw1 = new StringWriter();
        BufferedWriter bw1 = new BufferedWriter(sw1, 1);
        bw1.write("ab");
        bw1.flush();
        if (!"ab".equals(sw1.toString())) {
            throw new RuntimeException("Unexpected output with sz=1: " + sw1.toString());
        }

        StringWriter sw2 = new StringWriter();
        BufferedWriter bw2 = new BufferedWriter(sw2);
        bw2.write("abc");
        bw2.flush();
        if (!"abc".equals(sw2.toString())) {
            throw new RuntimeException("Unexpected output with default size: " + sw2.toString());
        }

        StringWriter sw3 = new StringWriter();
        BufferedWriter bw3 = new BufferedWriter(sw3, 8);
        bw3.write('x');
        if (sw3.toString().length() != 0) {
            throw new RuntimeException("Output reached underlying writer before flush");
        }
        bw3.flush();
        if (!"x".equals(sw3.toString())) {
            throw new RuntimeException("Unexpected output after flush: " + sw3.toString());
        }
    }

    private static void checkRejected(int sz) {
        try {
            new BufferedWriter(new StringWriter(), sz);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for sz=" + sz);
    }
}
