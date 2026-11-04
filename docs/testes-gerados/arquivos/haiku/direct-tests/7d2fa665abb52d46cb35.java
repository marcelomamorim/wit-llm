/*
 * @test
 * @summary Verifies InputStream.read(byte[]) delegates to read(byte[], int, int) and fills the buffer correctly
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    static class SeqStream extends InputStream {
        private final int[] data;
        private int pos;

        SeqStream(int... data) {
            this.data = data;
        }

        @Override
        public int read() throws IOException {
            if (pos >= data.length) {
                return -1;
            }
            return data[pos++];
        }
    }

    public static void main(String[] args) throws Exception {
        // Buffer larger than available data: returns count of bytes read
        byte[] buf = new byte[5];
        InputStream in = new SeqStream(1, 2, 3);
        int n = in.read(buf);
        check(n == 3, "expected 3 but got " + n);
        check(buf[0] == 1 && buf[1] == 2 && buf[2] == 3, "unexpected data");
        check(buf[3] == 0 && buf[4] == 0, "bytes beyond read count modified");

        // End of stream: returns -1
        n = in.read(buf);
        check(n == -1, "expected -1 at end of stream but got " + n);

        // Buffer smaller than available data: reads only buffer length
        byte[] buf2 = new byte[2];
        n = new SeqStream(7, 8, 9).read(buf2);
        check(n == 2, "expected 2 but got " + n);
        check(buf2[0] == 7 && buf2[1] == 8, "unexpected data in small buffer");

        // Zero-length buffer returns 0
        n = new SeqStream(1).read(new byte[0]);
        check(n == 0, "expected 0 for empty buffer but got " + n);

        // Byte value above 127 is stored as signed byte
        byte[] buf3 = new byte[1];
        new SeqStream(200).read(buf3);
        check(buf3[0] == (byte) 200, "unexpected byte value");

        // Empty null input stream returns end of stream
        n = InputStream.nullInputStream().read(new byte[4]);
        check(n == -1, "expected -1 from nullInputStream but got " + n);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
