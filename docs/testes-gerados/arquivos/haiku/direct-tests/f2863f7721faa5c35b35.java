/*
 * @test
 * @summary Witup-generated tests for DataInputStream.read(byte[]) delegation and observable results
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataInputStreamWitupTest {

    static class RecordingInputStream extends InputStream {
        int lastOff = -1;
        int lastLen = -1;
        int calls = 0;

        @Override
        public int read() throws IOException {
            return -1;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            lastOff = off;
            lastLen = len;
            calls++;
            return 7;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testReadFullBuffer() throws IOException {
        byte[] src = {1, 2, 3, 4, 5};
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(src));
        byte[] buf = new byte[4];
        int n = dis.read(buf);
        check(n == 4, "expected 4 bytes read, got " + n);
        check(buf[0] == 1 && buf[1] == 2 && buf[2] == 3 && buf[3] == 4,
              "unexpected buffer content");
    }

    static void testPartialRead() throws IOException {
        byte[] src = {9, 8, 7};
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(src));
        byte[] buf = new byte[10];
        int n = dis.read(buf);
        check(n == 3, "expected 3 bytes read, got " + n);
        check(buf[0] == 9 && buf[1] == 8 && buf[2] == 7, "unexpected prefix");
        check(buf[3] == 0, "bytes past count must be unaffected");
    }

    static void testEndOfStreamReturnsMinusOne() throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(new byte[0]));
        int n = dis.read(new byte[8]);
        check(n == -1, "expected -1 at EOF, got " + n);
    }

    static void testZeroLengthArrayReturnsZero() throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(new byte[] {1, 2}));
        int n = dis.read(new byte[0]);
        check(n == 0, "expected 0 for zero-length buffer, got " + n);
    }

    static void testDelegatesWithFullLength() throws IOException {
        RecordingInputStream in = new RecordingInputStream();
        DataInputStream dis = new DataInputStream(in);
        byte[] buf = new byte[13];
        int n = dis.read(buf);
        check(n == 7, "return value must be propagated from underlying stream");
        check(in.calls == 1, "underlying read must be invoked exactly once");
        check(in.lastOff == 0, "offset must be 0, got " + in.lastOff);
        check(in.lastLen == buf.length, "length must be b.length, got " + in.lastLen);
    }

    static void testNullBufferThrowsNPE() throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(new byte[] {1}));
        boolean thrown = false;
        try {
            dis.read((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null buffer must raise NullPointerException");
    }

    public static void main(String[] args) throws Exception {
        testReadFullBuffer();
        testPartialRead();
        testEndOfStreamReturnsMinusOne();
        testZeroLengthArrayReturnsZero();
        testDelegatesWithFullLength();
        testNullBufferThrowsNPE();
    }
}
