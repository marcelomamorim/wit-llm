/*
 * @test
 * @summary Tests argument handling of StreamEncoder.write(String, int, int)
 * @modules java.base/sun.nio.cs
 * @run main StreamEncoderWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import sun.nio.cs.StreamEncoder;

public class StreamEncoderWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeLengthThrows();
        testValidSubstring();
        testZeroLengthWritesNothing();
        testOutOfRangeThrowsStringIndexOutOfBounds();
    }

    private static StreamEncoder newEncoder(ByteArrayOutputStream out) {
        return StreamEncoder.forOutputStreamWriter(out, new Object(), StandardCharsets.UTF_8);
    }

    private static void testNegativeLengthThrows() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StreamEncoder enc = newEncoder(out);
        try {
            enc.write("abc", 0, -1);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for negative len");
    }

    private static void testValidSubstring() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StreamEncoder enc = newEncoder(out);
        enc.write("hello world", 6, 5);
        enc.flushBuffer();
        String result = out.toString("UTF-8");
        if (!"world".equals(result)) {
            throw new RuntimeException("Expected 'world' but got '" + result + "'");
        }
    }

    private static void testZeroLengthWritesNothing() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StreamEncoder enc = newEncoder(out);
        enc.write("abc", 0, 0);
        enc.flushBuffer();
        if (out.size() != 0) {
            throw new RuntimeException("Expected no bytes but got " + out.size());
        }
    }

    private static void testOutOfRangeThrowsStringIndexOutOfBounds() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StreamEncoder enc = newEncoder(out);
        try {
            enc.write("abc", 3, 10);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected StringIndexOutOfBoundsException for off+len > length");
    }
}
