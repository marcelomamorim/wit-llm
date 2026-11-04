/*
 * @test
 * @summary Test InputStream.read(byte[]) method delegates correctly to read(byte[], int, int)
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    static class TestInputStream extends InputStream {
        byte[] lastBuffer;
        int lastOff = -1;
        int lastLen = -1;
        int toReturn;

        TestInputStream(int toReturn) {
            this.toReturn = toReturn;
        }

        @Override
        public int read() throws IOException {
            return -1; // not used in this test
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            lastBuffer = b;
            lastOff = off;
            lastLen = len;
            return toReturn;
        }
    }

    public static void main(String[] args) throws IOException {
        TestInputStream tis = new TestInputStream(42);
        byte[] buffer = new byte[10];
        int result = tis.read(buffer);

        if (result != 42) {
            throw new AssertionError("Expected return 42 but got " + result);
        }
        if (tis.lastBuffer != buffer) {
            throw new AssertionError("Buffer passed to read(byte[], int, int) differs from argument");
        }
        if (tis.lastOff != 0) {
            throw new AssertionError("Offset passed to read(byte[], int, int) is not zero");
        }
        if (tis.lastLen != buffer.length) {
            throw new AssertionError("Length passed to read(byte[], int, int) is not buffer.length");
        }
    }
}
