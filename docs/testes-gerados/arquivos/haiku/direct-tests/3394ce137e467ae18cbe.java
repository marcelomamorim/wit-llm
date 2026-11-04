/*
 * @test
 * @summary Tests BufferedInputStream(InputStream, int) constructor: rejects non-positive sizes and buffers data correctly with small buffers
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNonPositiveSizeRejected();
        testReadsAllBytesWithSmallBuffer();
        testMarkResetWithSmallBuffer();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonPositiveSizeRejected() {
        int[] sizes = {0, -1, Integer.MIN_VALUE};
        for (int size : sizes) {
            boolean thrown = false;
            try {
                new BufferedInputStream(new ByteArrayInputStream(new byte[1]), size);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "expected IllegalArgumentException for size " + size);
        }
    }

    static void testReadsAllBytesWithSmallBuffer() throws IOException {
        byte[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data), 4);
        for (int i = 0; i < data.length; i++) {
            int v = in.read();
            check(v == (data[i] & 0xff), "byte mismatch at index " + i + ": " + v);
        }
        check(in.read() == -1, "expected end of stream");
        in.close();
    }

    static void testMarkResetWithSmallBuffer() throws IOException {
        byte[] data = {'a', 'b', 'c', 'd', 'e', 'f'};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data), 2);
        in.mark(10);
        for (int i = 0; i < 4; i++) {
            in.read();
        }
        in.reset();
        for (int i = 0; i < data.length; i++) {
            int v = in.read();
            check(v == (data[i] & 0xff), "after reset mismatch at index " + i + ": " + v);
        }
        check(in.read() == -1, "expected end of stream after reset read");
        in.close();
    }
}
