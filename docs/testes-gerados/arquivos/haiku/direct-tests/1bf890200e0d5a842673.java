/*
 * @test
 * @summary Verifies the default-buffer constructor BufferedWriter(Writer) buffers output with an 8192-char buffer
 * @run main BufferedWriterWitupTest
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

public class BufferedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testSmallWriteIsBufferedUntilFlush();
        testWriteBelowDefaultSizeIsNotForwarded();
        testDefaultBufferFlushesAtEightThousandNineHundredNinetyTwo();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSmallWriteIsBufferedUntilFlush() throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write("abc");
        check(sw.toString().isEmpty(), "small write should remain buffered");
        bw.flush();
        check("abc".equals(sw.toString()), "flush should forward buffered text");
        bw.close();
    }

    static void testWriteBelowDefaultSizeIsNotForwarded() throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8191; i++) {
            sb.append('x');
        }
        bw.write(sb.toString());
        check(sw.toString().isEmpty(), "8191 chars should not reach underlying writer before flush");
        bw.flush();
        check(sw.toString().length() == 8191, "flush should forward all 8191 chars");
        bw.close();
    }

    static void testDefaultBufferFlushesAtEightThousandNineHundredNinetyTwo() throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8193; i++) {
            sb.append('y');
        }
        bw.write(sb.toString());
        check(sw.toString().length() == 8192,
              "default buffer should auto-flush exactly 8192 chars, got " + sw.toString().length());
        bw.flush();
        check(sw.toString().length() == 8193, "flush should forward remaining char");
        bw.close();
    }
}
