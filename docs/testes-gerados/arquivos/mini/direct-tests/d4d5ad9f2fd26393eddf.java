/*
 * @test
 * @summary Test FilterOutputStream.write(byte[]) method delegates correctly
 * @run main FilterOutputStreamWitupTest
 */

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.IOException;

public class FilterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteDelegatesCorrectly();
        testWriteWithEmptyArray();
    }

    static void testWriteDelegatesCorrectly() throws IOException {
        final byte[] data = {1, 2, 3, 4, 5};
        final TestOutputStream tos = new TestOutputStream();
        FilterOutputStream fos = new FilterOutputStream(tos);
        fos.write(data);
        if (tos.writeCalledCount != 1) {
            throw new AssertionError("write(byte[], int, int) should be called once");
        }
        if (tos.lastOffset != 0 || tos.lastLength != data.length) {
            throw new AssertionError("write called with incorrect offset or length");
        }
        if (tos.lastWritten == null || tos.lastWritten.length != data.length) {
            throw new AssertionError("write called with incorrect byte array length");
        }
        for (int i = 0; i < data.length; i++) {
            if (tos.lastWritten[i] != data[i]) {
                throw new AssertionError("write called with incorrect data at index " + i);
            }
        }
    }

    static void testWriteWithEmptyArray() throws IOException {
        final byte[] empty = new byte[0];
        final TestOutputStream tos = new TestOutputStream();
        FilterOutputStream fos = new FilterOutputStream(tos);
        fos.write(empty);
        if (tos.writeCalledCount != 1) {
            throw new AssertionError("write(byte[], int, int) should be called once even for empty array");
        }
        if (tos.lastOffset != 0 || tos.lastLength != 0) {
            throw new AssertionError("write called with incorrect offset or length for empty array");
        }
    }

    // Helper OutputStream to record calls to write(byte[], int, int)
    static class TestOutputStream extends OutputStream {
        int writeCalledCount = 0;
        byte[] lastWritten = null;
        int lastOffset = -1;
        int lastLength = -1;

        @Override
        public void write(int b) throws IOException {
            // Not used in these tests
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            writeCalledCount++;
            lastWritten = new byte[len];
            System.arraycopy(b, off, lastWritten, 0, len);
            lastOffset = off;
            lastLength = len;
        }
    }
}
