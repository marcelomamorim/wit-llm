/*
 * @test
 * @summary Verifies FilterOutputStream.write(byte[]) delegates to write(byte[], int, int) with offset 0 and full length, and propagates bytes to the underlying stream.
 * @run main FilterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FilterOutputStreamWitupTest {

    static class RecordingFilter extends FilterOutputStream {
        int arrayWrites;
        int lastOff = -1;
        int lastLen = -1;

        RecordingFilter(OutputStream out) {
            super(out);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            arrayWrites++;
            lastOff = off;
            lastLen = len;
            super.write(b, off, len);
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testDelegatesToThreeArgWrite() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        RecordingFilter f = new RecordingFilter(sink);
        byte[] data = {1, 2, 3, 4, 5};
        f.write(data);
        check(f.arrayWrites == 1, "expected exactly one 3-arg write, got " + f.arrayWrites);
        check(f.lastOff == 0, "expected offset 0, got " + f.lastOff);
        check(f.lastLen == data.length, "expected length " + data.length + ", got " + f.lastLen);
        byte[] out = sink.toByteArray();
        check(out.length == data.length, "wrong output length " + out.length);
        for (int i = 0; i < data.length; i++) {
            check(out[i] == data[i], "byte mismatch at " + i);
        }
    }

    static void testEmptyArray() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        RecordingFilter f = new RecordingFilter(sink);
        f.write(new byte[0]);
        check(f.arrayWrites == 1, "expected one delegated call for empty array");
        check(f.lastLen == 0, "expected length 0");
        check(sink.size() == 0, "expected no bytes written");
    }

    static void testNullArrayThrowsNpe() {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        FilterOutputStream f = new FilterOutputStream(sink);
        boolean thrown = false;
        try {
            f.write((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        check(thrown, "expected NullPointerException for null array");
    }

    public static void main(String[] args) throws Exception {
        testDelegatesToThreeArgWrite();
        testEmptyArray();
        testNullArrayThrowsNpe();
    }
}
