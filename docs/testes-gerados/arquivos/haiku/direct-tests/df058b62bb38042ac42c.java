/*
 * @test
 * @summary Tests BufferedInputStream.read() returns unsigned bytes, -1 at EOF, correct refills, mark/reset interaction and IOException after close
 * @run main BufferedInputStreamWitupTest
 */

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testUnsignedBytesAndEof();
        testSmallBufferRefill();
        testMarkReset();
        testReadAfterCloseThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUnsignedBytesAndEof() throws Exception {
        byte[] data = {(byte) 0x80, (byte) 0xFF, (byte) 0x00, (byte) 0x7F};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data));
        check(in.read() == 128, "expected 128 for 0x80");
        check(in.read() == 255, "expected 255 for 0xFF");
        check(in.read() == 0, "expected 0 for 0x00");
        check(in.read() == 127, "expected 127 for 0x7F");
        check(in.read() == -1, "expected -1 at end of stream");
        check(in.read() == -1, "expected -1 on repeated read at EOF");
        in.close();
    }

    static void testSmallBufferRefill() throws Exception {
        byte[] data = new byte[1000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i * 7);
        }
        BufferedInputStream in =
                new BufferedInputStream(new ByteArrayInputStream(data), 1);
        for (int i = 0; i < data.length; i++) {
            int v = in.read();
            check(v == (data[i] & 0xff), "mismatch at index " + i);
        }
        check(in.read() == -1, "expected -1 after all bytes read");
        in.close();
    }

    static void testMarkReset() throws Exception {
        byte[] data = {1, 2, 3, 4, 5};
        BufferedInputStream in =
                new BufferedInputStream(new ByteArrayInputStream(data), 4);
        in.mark(10);
        check(in.read() == 1, "expected 1");
        check(in.read() == 2, "expected 2");
        check(in.read() == 3, "expected 3");
        in.reset();
        check(in.read() == 1, "expected 1 after reset");
        check(in.read() == 2, "expected 2 after reset");
        check(in.read() == 3, "expected 3 after reset");
        check(in.read() == 4, "expected 4");
        check(in.read() == 5, "expected 5");
        check(in.read() == -1, "expected -1 at end");
        in.close();
    }

    static void testReadAfterCloseThrows() throws Exception {
        BufferedInputStream in =
                new BufferedInputStream(new ByteArrayInputStream(new byte[] {1, 2, 3}));
        in.close();
        try {
            in.read();
            throw new RuntimeException("expected IOException after close");
        } catch (IOException expected) {
            // expected: stream is closed
        }
    }
}
