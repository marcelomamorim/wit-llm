/*
 * @test
 * @summary Tests for java.util.zip.InflaterInputStream.skip(long)
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testSkipNegative();
        testSkipWhenClosed();
    }

    private static void testSkipNegative() {
        InputStreamStub in = new InputStreamStub();
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(in, inflater);
        try {
            long result = iis.skip(-1);
            // Expect IllegalArgumentException, so if no exception, fail
            System.err.println("Expected IllegalArgumentException for negative skip, but got result: " + result);
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        } finally {
            try {
                iis.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    private static void testSkipWhenClosed() throws IOException {
        InputStreamStub in = new InputStreamStub();
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(in, inflater);
        iis.close();
        try {
            long result = iis.skip(10);
            System.err.println("Expected IOException for skip after close, but got result: " + result);
        } catch (IOException e) {
            // Expected
        }
    }

    static class InputStreamStub extends ByteArrayInputStream {
        public InputStreamStub() {
            super(new byte[0]);
        }
        @Override
        public int read() {
            return -1;
        }
        @Override
        public int read(byte[] b, int off, int len) {
            return -1;
        }
        @Override
        public void close() throws IOException {
            super.close();
        }
    }
}
