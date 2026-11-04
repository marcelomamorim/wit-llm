/*
 * @test
 * @summary Test deterministic behavior of javax.crypto.CipherInputStream.read()
 * @run main javax.crypto.CipherInputStreamWitupTest
 */

import javax.crypto.CipherInputStream;
import javax.crypto.Cipher;
import java.io.InputStream;
import java.io.IOException;

public class CipherInputStreamWitupTest {

    // A minimal InputStream that returns EOF immediately
    static class EmptyInputStream extends InputStream {
        public int read() throws IOException {
            return -1;
        }
    }

    // A minimal Cipher stub that does no transformation
    static class IdentityCipher extends Cipher {
        protected IdentityCipher() {
            super(null, null, null);
        }
    }

    public static void main(String[] args) throws Exception {
        testReadReturnsMinusOneOnEmptyStream();
        testReadReturnsByteAfterDataAvailable();
    }

    private static void testReadReturnsMinusOneOnEmptyStream() throws IOException {
        InputStream empty = new EmptyInputStream();
        Cipher dummyCipher = new DummyCipher();
        CipherInputStream cis = new CipherInputStream(empty, dummyCipher);

        int result = cis.read();
        if (result != -1) {
            throw new AssertionError("Expected -1 on empty stream, got: " + result);
        }
    }

    private static void testReadReturnsByteAfterDataAvailable() throws IOException {
        InputStream singleByteStream = new InputStream() {
            private boolean readOnce = false;
            public int read() throws IOException {
                if (readOnce) return -1;
                readOnce = true;
                return 0xAB;
            }
        };

        Cipher dummyCipher = new DummyCipher();
        CipherInputStream cis = new CipherInputStream(singleByteStream, dummyCipher);

        int firstRead = cis.read();
        if (firstRead != (0xAB & 0xFF)) {
            throw new AssertionError("Expected 0xAB on first read, got: " + firstRead);
        }

        int secondRead = cis.read();
        if (secondRead != -1) {
            throw new AssertionError("Expected -1 after stream end, got: " + secondRead);
        }
    }

    // DummyCipher subclass to satisfy constructor, does not transform data
    static class DummyCipher extends Cipher {
        protected DummyCipher() {
            super(null, null, null);
        }
    }
}
