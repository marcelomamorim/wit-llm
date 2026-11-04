/*
 * @test
 * @summary Test PrintStream(OutputStream out) constructor basic behavior
 * @run main PrintStreamWitupTest
 */

import java.io.PrintStream;
import java.io.OutputStream;
import java.io.IOException;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        // Test with a simple OutputStream that counts bytes written
        CountingOutputStream cos = new CountingOutputStream();
        PrintStream ps = new PrintStream(cos);

        // Write some bytes and check that they are forwarded
        ps.write('A');
        ps.write('B');
        ps.flush();

        if (cos.getCount() != 2) {
            throw new RuntimeException("Expected 2 bytes written, got " + cos.getCount());
        }

        // Test that no error is reported initially
        if (ps.checkError()) {
            throw new RuntimeException("PrintStream reports error unexpectedly");
        }

        // Close the PrintStream and underlying stream
        ps.close();
        if (!cos.isClosed()) {
            throw new RuntimeException("Underlying OutputStream not closed after PrintStream.close()");
        }
    }

    // Simple OutputStream that counts bytes written and tracks close
    static class CountingOutputStream extends OutputStream {
        private int count = 0;
        private boolean closed = false;

        @Override
        public void write(int b) throws IOException {
            count++;
        }

        @Override
        public void close() throws IOException {
            closed = true;
            super.close();
        }

        int getCount() {
            return count;
        }

        boolean isClosed() {
            return closed;
        }
    }
}
