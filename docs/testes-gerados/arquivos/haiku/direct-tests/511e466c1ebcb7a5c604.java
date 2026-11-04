/*
 * @test
 * @summary Verifies StreamEncoder.write(String, int, int) writes the requested substring, rejects negative length, and fails after close
 * @modules java.base/sun.nio.cs
 * @run main StreamEncoderWitupTest
 */
import java.io.*;
import java.nio.charset.StandardCharsets;
import sun.nio.cs.StreamEncoder;

public class StreamEncoderWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteSubstring();
        testWriteZeroLength();
        testNegativeLengthThrows();
        testWriteAfterCloseThrows();
    }

    static StreamEncoder newEncoder(ByteArrayOutputStream bos) {
        return StreamEncoder.forOutputStreamWriter(bos, new Object(), StandardCharsets.UTF_8);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testWriteSubstring() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamEncoder enc = newEncoder(bos);
        enc.write("abcdef", 2, 3);
        enc.flush();
        check("cde".equals(bos.toString("UTF-8")),
              "Expected \"cde\" but got \"" + bos.toString("UTF-8") + "\"");
    }

    static void testWriteZeroLength() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamEncoder enc = newEncoder(bos);
        enc.write("abc", 1, 0);
        enc.flush();
        check(bos.size() == 0, "Expected no output for zero-length write");
    }

    static void testNegativeLengthThrows() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamEncoder enc = newEncoder(bos);
        boolean thrown = false;
        try {
            enc.write("abc", 0, -1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "Expected IndexOutOfBoundsException for negative length");
    }

    static void testWriteAfterCloseThrows() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamEncoder enc = newEncoder(bos);
        enc.close();
        boolean thrown = false;
        try {
            enc.write("x", 0, 1);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "Expected IOException when writing to a closed StreamEncoder");
    }
}
