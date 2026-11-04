/*
 * @test
 * @summary Verifies FilterOutputStream.write(byte[]) delegates all bytes to the underlying stream and rejects null arrays with NullPointerException
 * @run main FilterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class FilterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWritesAllBytesToUnderlyingStream();
        testEmptyArrayWritesNothing();
        testNullArrayThrowsNullPointerException();
    }

    static void testWritesAllBytesToUnderlyingStream() throws IOException {
        byte[] data = {1, 2, 3, (byte) 0xFF};
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        OutputStream f = new FilterOutputStream(sink);
        f.write(data);
        byte[] actual = sink.toByteArray();
        check(Arrays.equals(data, actual), "write(byte[]) did not forward all bytes: " + Arrays.toString(actual));
    }

    static void testEmptyArrayWritesNothing() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        OutputStream f = new FilterOutputStream(sink);
        f.write(new byte[0]);
        check(sink.size() == 0, "empty array produced output of size " + sink.size());
    }

    static void testNullArrayThrowsNullPointerException() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        OutputStream f = new FilterOutputStream(sink);
        boolean thrown = false;
        try {
            f.write((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "write((byte[]) null) did not throw NullPointerException");
        check(sink.size() == 0, "null array produced output");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
