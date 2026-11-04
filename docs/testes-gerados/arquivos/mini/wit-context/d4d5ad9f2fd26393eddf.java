/*
 * @test
 * @summary Test FilterOutputStream.write(byte[]) method for normal and boundary cases
 * @run main FilterOutputStreamWitupTest
 */

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.IOException;

public class FilterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteNullArray();
        testWriteEmptyArray();
        testWriteNormalArray();
    }

    private static void testWriteNullArray() {
        OutputStream dummyOut = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // do nothing
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                // do nothing
            }
        };
        FilterOutputStream fos = new FilterOutputStream(dummyOut);
        try {
            fos.write(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testWriteEmptyArray() throws IOException {
        OutputStream dummyOut = new OutputStream() {
            boolean writeCalled = false;
            @Override
            public void write(int b) throws IOException {
                throw new RuntimeException("write(int) should not be called");
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                if (b == null) {
                    throw new NullPointerException();
                }
                if (off != 0 || len != 0) {
                    throw new RuntimeException("Unexpected offset or length for empty array");
                }
                writeCalled = true;
            }
        };
        FilterOutputStream fos = new FilterOutputStream(dummyOut);
        fos.write(new byte[0]); // should call write(byte[],0,0) without error
    }

    private static void testWriteNormalArray() throws IOException {
        final byte[] data = {1, 2, 3, 4, 5};
        OutputStream dummyOut = new OutputStream() {
            byte[] lastWritten = null;
            int lastOff = -1;
            int lastLen = -1;

            @Override
            public void write(int b) throws IOException {
                throw new RuntimeException("write(int) should not be called");
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                if (b == null) {
                    throw new NullPointerException();
                }
                lastWritten = b;
                lastOff = off;
                lastLen = len;
                if (off != 0 || len != b.length) {
                    throw new RuntimeException("Unexpected offset or length");
                }
            }
        };
        FilterOutputStream fos = new FilterOutputStream(dummyOut);
        fos.write(data);
    }
}
