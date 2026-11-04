/*
 * @test
 * @summary Verifies that OutputStream.write(byte[]) delegates to write(byte[], int, int) with offset 0 and full length, and that a null array causes NullPointerException
 * @run main OutputStreamWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamWitupTest {

    static class RecordingStream extends OutputStream {
        int calls;
        int lastOff = -1;
        int lastLen = -1;
        final int[] data = new int[100];
        int count;

        @Override
        public void write(int b) throws IOException {
            data[count++] = b & 0xFF;
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            calls++;
            lastOff = off;
            lastLen = len;
            for (int i = 0; i < len; i++) {
                data[count++] = b[off + i] & 0xFF;
            }
        }
    }

    static class ByteOnlyStream extends OutputStream {
        final int[] data = new int[100];
        int count;

        @Override
        public void write(int b) throws IOException {
            data[count++] = b & 0xFF;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] in = {1, 2, 3, 4, 5};

        RecordingStream s = new RecordingStream();
        s.write(in);
        check(s.calls == 1, "expected one delegated call, got " + s.calls);
        check(s.lastOff == 0, "expected offset 0, got " + s.lastOff);
        check(s.lastLen == in.length, "expected length " + in.length + ", got " + s.lastLen);
        check(s.count == in.length, "expected " + in.length + " bytes written");
        for (int i = 0; i < in.length; i++) {
            check(s.data[i] == (in[i] & 0xFF), "byte mismatch at " + i);
        }

        RecordingStream empty = new RecordingStream();
        empty.write(new byte[0]);
        check(empty.calls == 1, "empty array should still delegate once");
        check(empty.lastLen == 0, "expected length 0 for empty array");
        check(empty.count == 0, "no bytes should be written for empty array");

        ByteOnlyStream b = new ByteOnlyStream();
        b.write(in);
        check(b.count == in.length, "default path should write each byte via write(int)");
        for (int i = 0; i < in.length; i++) {
            check(b.data[i] == (in[i] & 0xFF), "default path byte mismatch at " + i);
        }

        boolean npe = false;
        try {
            s.write((byte[]) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null array should throw NullPointerException");
    }
}
