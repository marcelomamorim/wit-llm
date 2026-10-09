/* @test
   @summary Testa o método readNBytes de InputStream para entradas válidas e inválidas.
   @run main InputStreamWitupTest_P325172594888
 */
import java.io.InputStream;
import java.io.IOException;
import java.util.Arrays;

public class InputStreamWitupTest_P325172594888 {
    public static void main(String[] args) throws Exception {
        testReadNBytesNegative();
        testReadNBytesZero();
        testReadNBytesPositive();
        testReadNBytesLarge();
    }

    private static void testReadNBytesNegative() {
        InputStream is = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return 0;
            }
        };
        try {
            is.readNBytes(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative len");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testReadNBytesZero() throws IOException {
        InputStream is = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return -1;
            }
        };
        byte[] result = is.readNBytes(0);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array for zero length");
        }
    }

    private static void testReadNBytesPositive() throws IOException {
        InputStream is = new InputStream() {
            private int count = 0;
            @Override
            public int read(byte[] b, int off, int len) {
                if (count >= 3) return -1;
                int toRead = Math.min(len, 3 - count);
                for (int i = 0; i < toRead; i++) {
                    b[off + i] = (byte) (count + i);
                }
                count += toRead;
                return toRead;
            }
        };
        byte[] result = is.readNBytes(5);
        if (result.length != 3) {
            throw new AssertionError("Expected 3 bytes read");
        }
        byte[] expected = new byte[] {0, 1, 2};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Bytes read do not match expected");
        }
    }

    private static void testReadNBytesLarge() throws IOException {
        InputStream is = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) {
                for (int i = 0; i < len; i++) {
                    b[off + i] = (byte) i;
                }
                return len;
            }
        };
        byte[] result = is.readNBytes(4);
        if (result.length != 4) {
            throw new AssertionError("Expected 4 bytes");
        }
        byte[] expected = new byte[] {0, 1, 2, 3};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Bytes do not match expected");
        }
    }
}
