/*
 * @test
 * @summary Verify StringWriter.write(char[], int, int) bounds checks and output
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) {
        char[] data = {'a', 'b', 'c', 'd'};

        StringWriter sw = new StringWriter();
        sw.write(data, 1, 2);
        check("bc".equals(sw.toString()), "normal write should append 'bc'");

        expectIOOBE(sw, data, -1, 1);
        expectIOOBE(sw, data, 0, -1);
        expectIOOBE(sw, data, 5, 0);
        expectIOOBE(sw, data, 3, 2);
        expectIOOBE(sw, data, 1, Integer.MAX_VALUE);

        check("bc".equals(sw.toString()), "failed writes must not modify output");

        StringWriter sw2 = new StringWriter();
        sw2.write(data, data.length, 0);
        check(sw2.toString().isEmpty(), "zero-length write at end should append nothing");
    }

    static void expectIOOBE(StringWriter sw, char[] data, int off, int len) {
        try {
            sw.write(data, off, len);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for off=" + off + " len=" + len);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
