/*
 * @test
 * @summary Witup-derived behavioral tests for BufferedInputStream.read() covering sequential reads, EOF, byte masking, mark/reset interaction, small buffers and closed-stream IOException
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSequentialReadAndEof();
        testByteMaskingTo0xff();
        testMarkResetRereads();
        testReadAfterCloseThrowsIOException();
        testRepeatedEofReturnsMinusOne();
        testSmallBufferSizeReadsAllBytes();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequentialReadAndEof() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data));
        for (int i = 0; i < data.length; i++) {
            check(in.read() == data[i], "sequential read mismatch at index " + i);
        }
        check(in.read() == -1, "expected EOF -1 after all bytes");
        in.close();
    }

    static void testByteMaskingTo0xff() throws IOException {
        byte[] data = {(byte) 0xFF, 0x00, (byte) 0x80};
        int[] expected = {255, 0, 128};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data));
        for (int i = 0; i < expected.length; i++) {
            check(in.read() == expected[i], "masking mismatch at index " + i);
        }
        check(in.read() == -1, "expected EOF after masked bytes");
        in.close();
    }

    static void testMarkResetRereads() throws IOException {
        byte[] data = {'a', 'b', 'c', 'd', 'e', 'f'};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data), 4);
        in.mark(10);
        check(in.read() == 'a', "expected a before reset");
        check(in.read() == 'b', "expected b before reset");
        check(in.read() == 'c', "expected c before reset");
        in.reset();
        check(in.read() == 'a', "expected a after reset");
        check(in.read() == 'b', "expected b after reset");
        check(in.read() == 'c', "expected c after reset");
        check(in.read() == 'd', "expected d after reset");
        check(in.read() == 'e', "expected e after reset");
        check(in.read() == 'f', "expected f after reset");
        check(in.read() == -1, "expected EOF after reset sequence");
        in.close();
    }

    static void testReadAfterCloseThrowsIOException() throws IOException {
        byte[] data = {10, 20, 30};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data));
        in.close();
        boolean thrown = false;
        try {
            in.read();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "read after close must throw IOException");
    }

    static void testRepeatedEofReturnsMinusOne() throws IOException {
        byte[] data = new byte[0];
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data));
        for (int i = 0; i < 3; i++) {
            check(in.read() == -1, "expected -1 on empty stream attempt " + i);
        }
        in.close();
    }

    static void testSmallBufferSizeReadsAllBytes() throws IOException {
        int total = 300;
        byte[] data = new byte[total];
        for (int i = 0; i < total; i++) {
            data[i] = (byte) (i % 256);
        }
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data), 1);
        for (int i = 0; i < total; i++) {
            int expected = data[i] & 0xff;
            check(in.read() == expected, "small buffer mismatch at index " + i);
        }
        check(in.read() == -1, "expected EOF after small buffer reads");
        in.close();
    }
}
