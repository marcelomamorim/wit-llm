/* @test    @summary Test readNBytes with valid input and negative input    @run main InputStreamWitupTest_P84ea176d10cc */

import java.io.InputStream;
import java.io.IOException;
import java.util.Arrays;

public class InputStreamWitupTest_P84ea176d10cc {

    public static void main(String[] args) throws Exception {
        testReadNBytesZeroLength();
        testReadNBytesPositiveLength();
        testReadNBytesNegativeLength();
    }

    static class DummyInputStream extends InputStream {
        private final byte[] data;
        private int position = 0;

        DummyInputStream(byte[] data) {
            this.data = data;
        }

        @Override
        public int read(byte[] b, int off, int len) {
            if (b == null) throw new NullPointerException();
            if (off < 0 || len < 0 || len > b.length - off) throw new IndexOutOfBoundsException();
            if (position >= data.length) return -1;
            int toRead = Math.min(len, data.length - position);
            System.arraycopy(data, position, b, off, toRead);
            position += toRead;
            return toRead;
        }
    }

    static void testReadNBytesZeroLength() throws IOException {
        byte[] sampleData = {1, 2, 3};
        InputStream is = new DummyInputStream(sampleData);
        byte[] result = is.readNBytes(0);
        assert result != null : "Result should not be null";
        assert result.length == 0 : "Expected empty array";
    }

    static void testReadNBytesPositiveLength() throws IOException {
        byte[] sampleData = {10, 20, 30, 40, 50};
        InputStream is = new DummyInputStream(sampleData);
        byte[] result = is.readNBytes(3);
        assert result != null : "Result should not be null";
        assert result.length == 3 : "Expected length 3";
        assert Arrays.equals(result, new byte[]{10, 20, 30}) : "Unexpected data";
    }

    static void testReadNBytesNegativeLength() {
        byte[] sampleData = {1, 2, 3};
        InputStream is = new DummyInputStream(sampleData);
        try {
            is.readNBytes(-1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("len < 0") : "Unexpected message";
        } catch (Exception e) {
            assert false : "Unexpected exception type";
        }
    }
}
