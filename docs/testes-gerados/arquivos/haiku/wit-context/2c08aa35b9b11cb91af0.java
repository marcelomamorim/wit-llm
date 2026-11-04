/*
 * @test
 * @summary Verifies OutputStream.write(byte[]) delegates to write(byte[], int, int) with offset 0 and full length, and checks null-array and closed-stream behavior
 * @run main OutputStreamWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testDelegationToThreeArgWrite();
        testNullArrayThrowsNullPointerException();
        testNullOutputStreamAcceptsArray();
        testClosedNullOutputStreamThrowsIOException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class Recorder extends OutputStream {
        int calls;
        byte[] seenArray;
        int seenOff = -1;
        int seenLen = -1;

        @Override
        public void write(int b) {
        }

        @Override
        public void write(byte[] b, int off, int len) {
            calls++;
            seenArray = b;
            seenOff = off;
            seenLen = len;
        }
    }

    static void testDelegationToThreeArgWrite() throws IOException {
        Recorder r = new Recorder();
        byte[] data = new byte[] {10, 20, 30, 40, 50};
        r.write(data);
        check(r.calls == 1, "write(byte[],int,int) should be called exactly once, was " + r.calls);
        check(r.seenArray == data, "array reference should be passed through unchanged");
        check(r.seenOff == 0, "offset should be 0, was " + r.seenOff);
        check(r.seenLen == data.length, "length should be data.length, was " + r.seenLen);

        Recorder empty = new Recorder();
        byte[] none = new byte[0];
        empty.write(none);
        check(empty.calls == 1, "empty array should still delegate once");
        check(empty.seenOff == 0 && empty.seenLen == 0, "empty array should give off=0, len=0");
    }

    static void testNullArrayThrowsNullPointerException() throws IOException {
        OutputStream s = OutputStream.nullOutputStream();
        try {
            s.write((byte[]) null);
            throw new RuntimeException("expected NullPointerException for null array");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullOutputStreamAcceptsArray() throws IOException {
        OutputStream s = OutputStream.nullOutputStream();
        s.write(new byte[] {1, 2, 3});
        s.write(new byte[0]);
        s.flush();
        s.close();
    }

    static void testClosedNullOutputStreamThrowsIOException() throws IOException {
        OutputStream s = OutputStream.nullOutputStream();
        s.close();
        try {
            s.write(new byte[] {1, 2});
            throw new RuntimeException("expected IOException after close");
        } catch (IOException expected) {
        }
    }
}
