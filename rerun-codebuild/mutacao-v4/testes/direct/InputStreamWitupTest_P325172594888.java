/* @test
   @summary Tests the readNBytes method of InputStream for correct behavior.
   @run main InputStreamWitupTest_P325172594888
 */
import java.io.InputStream;
import java.io.IOException;
import java.util.Arrays;

public class InputStreamWitupTest_P325172594888 {
    public static void main(String[] args) throws Exception {
        testReadNBytesZeroLength();
        testReadNBytesNegativeLength();
        testReadNBytesExact();
        testReadNBytesMoreThanAvailable();
        testReadNBytesPartial();
    }

    private static void testReadNBytesZeroLength() throws IOException {
        InputStream emptyStream = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) {
                return -1;
            }
        };
        byte[] result = emptyStream.readNBytes(0);
        assert result != null : "Result should not be null";
        assert result.length == 0 : "Expected empty array";
    }

    private static void testReadNBytesNegativeLength() {
        InputStream stream = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) {
                return -1;
            }
        };
        try {
            stream.readNBytes(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testReadNBytesExact() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream stream = new InputStream() {
            private int index = 0;
            @Override
            public int read(byte[] b, int off, int len) {
                if (index >= data.length) return -1;
                int toRead = Math.min(len, data.length - index);
                System.arraycopy(data, index, b, off, toRead);
                index += toRead;
                return toRead;
            }
        };
        byte[] result = stream.readNBytes(5);
        assert Arrays.equals(result, data) : "Data mismatch";
    }

    private static void testReadNBytesMoreThanAvailable() throws IOException {
        byte[] data = {10, 20, 30};
        InputStream stream = new InputStream() {
            private int index = 0;
            @Override
            public int read(byte[] b, int off, int len) {
                if (index >= data.length) return -1;
                int toRead = Math.min(len, data.length - index);
                System.arraycopy(data, index, b, off, toRead);
                index += toRead;
                return toRead;
            }
        };
        byte[] result = stream.readNBytes(10);
        assert Arrays.equals(result, data) : "Data mismatch when reading more than available";
    }

    private static void testReadNBytesPartial() throws IOException {
        byte[] data = {7, 8, 9};
        InputStream stream = new InputStream() {
            private int index = 0;
            @Override
            public int read(byte[] b, int off, int len) {
                if (index >= data.length) return -1;
                int toRead = Math.min(len, data.length - index);
                System.arraycopy(data, index, b, off, toRead);
                index += toRead;
                return toRead;
            }
        };
        byte[] result = stream.readNBytes(2);
        assert Arrays.equals(result, new byte[]{7,8}) : "Partial read mismatch";
    }
}
