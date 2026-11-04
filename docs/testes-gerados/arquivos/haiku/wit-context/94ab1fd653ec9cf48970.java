/*
 * @test
 * @summary Behavioral checks for InputStream.readNBytes(int): negative length, zero length, partial and end-of-stream reads
 * @run main InputStreamWitupTest
 */

public class InputStreamWitupTest {

    private static final class ArrayStream extends java.io.InputStream {
        private final byte[] data;
        private int pos;

        ArrayStream(byte[] data) {
            this.data = data;
        }

        @Override
        public int read() {
            if (pos >= data.length) {
                return -1;
            }
            return data[pos++] & 0xFF;
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testNegativeLengthThrows() throws Exception {
        ArrayStream in = new ArrayStream(new byte[] {1, 2, 3});
        try {
            in.readNBytes(-1);
            throw new RuntimeException("expected IllegalArgumentException for len < 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testZeroLengthReturnsEmpty() throws Exception {
        ArrayStream in = new ArrayStream(new byte[] {1, 2, 3});
        byte[] result = in.readNBytes(0);
        check(result != null, "readNBytes(0) returned null");
        check(result.length == 0, "readNBytes(0) returned non-empty array");
    }

    private static void testPartialRead() throws Exception {
        ArrayStream in = new ArrayStream(new byte[] {10, 20, 30, 40, 50});
        byte[] result = in.readNBytes(3);
        check(result.length == 3, "expected 3 bytes, got " + result.length);
        check(result[0] == 10 && result[1] == 20 && result[2] == 30,
              "unexpected content for partial read");
    }

    private static void testReadRequestedMoreThanAvailable() throws Exception {
        ArrayStream in = new ArrayStream(new byte[] {1, 2, 3, 4, 5});
        byte[] result = in.readNBytes(10);
        check(result.length == 5, "expected 5 bytes, got " + result.length);
        for (int i = 0; i < 5; i++) {
            check(result[i] == i + 1, "unexpected byte at index " + i);
        }
    }

    private static void testReadAtEndOfStreamReturnsEmpty() throws Exception {
        ArrayStream in = new ArrayStream(new byte[] {7});
        in.readNBytes(1);
        byte[] result = in.readNBytes(4);
        check(result != null, "readNBytes at EOF returned null");
        check(result.length == 0, "readNBytes at EOF returned non-empty array");
    }

    public static void main(String[] args) throws Exception {
        testNegativeLengthThrows();
        testZeroLengthReturnsEmpty();
        testPartialRead();
        testReadRequestedMoreThanAvailable();
        testReadAtEndOfStreamReturnsEmpty();
    }
}
