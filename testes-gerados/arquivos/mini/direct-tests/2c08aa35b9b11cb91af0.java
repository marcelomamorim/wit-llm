/*
 * @test
 * @summary Test OutputStream.write(byte[]) method delegates correctly to write(byte[], int, int)
 * @run main OutputStreamWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamWitupTest {

    static class TestOutputStream extends OutputStream {
        byte[] lastWritten = null;
        int lastOff = -1;
        int lastLen = -1;
        boolean writeCalled = false;

        @Override
        public void write(int b) throws IOException {
            // Not used in this test
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            writeCalled = true;
            lastWritten = b;
            lastOff = off;
            lastLen = len;
        }
    }

    public static void main(String[] args) throws IOException {
        TestOutputStream tos = new TestOutputStream();
        byte[] data = {1, 2, 3, 4, 5};
        tos.write(data);

        if (!tos.writeCalled) {
            throw new AssertionError("write(byte[], int, int) was not called");
        }
        if (tos.lastWritten != data) {
            throw new AssertionError("write called with wrong byte array instance");
        }
        if (tos.lastOff != 0) {
            throw new AssertionError("write called with wrong offset: " + tos.lastOff);
        }
        if (tos.lastLen != data.length) {
            throw new AssertionError("write called with wrong length: " + tos.lastLen);
        }
    }
}
