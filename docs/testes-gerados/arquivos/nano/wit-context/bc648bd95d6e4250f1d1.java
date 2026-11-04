/* @test    @summary Testa o método skip de InputStream para garantir comportamento esperado em caso de stream fechado.    @run main InputStreamWitupTest */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    public static void main(String[] args) {
        testSkipOnClosedStream();
    }

    public static void testSkipOnClosedStream() {
        InputStream stream = new InputStream() {
            private boolean closed = false;

            @Override
            public int available() throws IOException {
                if (closed) throw new IOException("Stream closed");
                return 0;
            }

            @Override
            public int read() throws IOException {
                if (closed) throw new IOException("Stream closed");
                return -1;
            }

            @Override
            public long skip(long n) throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
                return 0L;
            }

            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            stream.close();
            long result = stream.skip(10);
            if (result != 0L) {
                throw new AssertionError("Expected skip to return 0 after close, but got " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage(), e);
        }
    }
}
