/*
 * @test
 * @summary Tests DataInputStream.readFully(byte[]) for full reads, empty arrays, EOF, null arrays and partial reads
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testFullRead();
        testEmptyArrayReadsNothing();
        testEofThrows();
        testNullArrayThrowsNpe();
        testPartialReadsAreAccumulated();
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testFullRead() throws IOException {
        byte[] src = {1, 2, 3, 4, 5};
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(src));
        byte[] b = new byte[5];
        in.readFully(b);
        if (!Arrays.equals(src, b)) {
            fail("readFully did not fill the buffer correctly");
        }
        if (in.read() != -1) {
            fail("stream should be exhausted after readFully");
        }
    }

    static void testEmptyArrayReadsNothing() throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(new byte[]{9}));
        in.readFully(new byte[0]);
        if (in.read() != 9) {
            fail("readFully with empty array should not consume bytes");
        }
    }

    static void testEofThrows() throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(new byte[]{1, 2}));
        boolean thrown = false;
        try {
            in.readFully(new byte[4]);
        } catch (EOFException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("expected EOFException when stream ends early");
        }
    }

    static void testNullArrayThrowsNpe() throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(new byte[]{1}));
        boolean thrown = false;
        try {
            in.readFully(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("expected NullPointerException for null array");
        }
    }

    static void testPartialReadsAreAccumulated() throws IOException {
        final byte[] data = {10, 20, 30, 40, 50, 60};
        InputStream oneByteAtATime = new InputStream() {
            private int pos = 0;

            @Override
            public int read() {
                return pos < data.length ? (data[pos++] & 0xFF) : -1;
            }

            @Override
            public int read(byte[] b, int off, int len) {
                if (len == 0) {
                    return 0;
                }
                if (pos >= data.length) {
                    return -1;
                }
                b[off] = data[pos++];
                return 1;
            }
        };
        DataInputStream in = new DataInputStream(oneByteAtATime);
        byte[] b = new byte[6];
        in.readFully(b);
        if (!Arrays.equals(data, b)) {
            fail("readFully should accumulate partial reads");
        }
    }
}
