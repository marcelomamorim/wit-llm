/*
 * @test
 * @summary Behavioral tests for InputStream.read(byte[]) delegating to read(byte[], int, int)
 * @run main InputStreamWitupTest
 */

public class InputStreamWitupTest {

    static final class ArrayStream extends InputStream {
        private final byte[] data;
        private int pos;

        ArrayStream(byte[] data) {
            this.data = data;
        }

        @Override
        public int read() throws IOException {
            if (pos >= data.length) {
                return -1;
            }
            return data[pos++] & 0xff;
        }
    }

    static final class FailingStream extends InputStream {
        @Override
        public int read() throws IOException {
            throw new IOException("boom");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testNullArrayThrowsNPE();
        testZeroLengthArrayReturnsZero();
        testPartialReadFillsRequestedBytes();
        testEndOfStreamReturnsMinusOne();
        testIOExceptionPropagates();
    }

    static void testNullArrayThrowsNPE() {
        try {
            new ArrayStream(new byte[1]).read((byte[]) null);
            check(false, "expected NullPointerException for null array");
        } catch (NullPointerException expected) {
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
    }

    static void testZeroLengthArrayReturnsZero() throws IOException {
        int n = new ArrayStream(new byte[]{1, 2, 3}).read(new byte[0]);
        check(n == 0, "expected 0 for zero-length array but was " + n);
    }

    static void testPartialReadFillsRequestedBytes() throws IOException {
        ArrayStream in = new ArrayStream(new byte[]{1, 2, 3, 4, 5});

        byte[] first = new byte[3];
        int n1 = in.read(first);
        check(n1 == 3, "expected 3 bytes on first read but was " + n1);
        check(first[0] == 1 && first[1] == 2 && first[2] == 3,
              "unexpected contents on first read");

        byte[] second = new byte[3];
        int n2 = in.read(second);
        check(n2 == 2, "expected 2 bytes on second read but was " + n2);
        check(second[0] == 4 && second[1] == 5, "unexpected contents on second read");

        int n3 = in.read(new byte[3]);
        check(n3 == -1, "expected -1 at end of stream but was " + n3);
    }

    static void testEndOfStreamReturnsMinusOne() throws IOException {
        int n = new ArrayStream(new byte[0]).read(new byte[4]);
        check(n == -1, "expected -1 for empty stream but was " + n);
    }

    static void testIOExceptionPropagates() {
        try {
            new FailingStream().read(new byte[4]);
            check(false, "expected IOException from underlying read()");
        } catch (IOException expected) {
            check("boom".equals(expected.getMessage()), "unexpected message: " + expected.getMessage());
        }
    }
}
