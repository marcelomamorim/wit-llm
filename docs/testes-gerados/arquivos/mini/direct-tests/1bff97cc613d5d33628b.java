/*
 * @test
 * @summary Test javax.crypto.CipherInputStream.read() method behavior
 * @run main javax.crypto.CipherInputStreamWitupTest
 */

import javax.crypto.CipherInputStream;
import javax.crypto.Cipher;
import java.io.InputStream;
import java.io.IOException;

public class CipherInputStreamWitupTest {

    // A dummy Cipher subclass that simulates a cipher with no data
    static class DummyCipher extends Cipher {
        protected DummyCipher() {
            super(null, null, null);
        }
    }

    // A simple InputStream that returns fixed bytes and then EOF
    static class FixedInputStream extends InputStream {
        private final byte[] data;
        private int pos = 0;

        FixedInputStream(byte[] data) {
            this.data = data;
        }

        @Override
        public int read() throws IOException {
            if (pos >= data.length) {
                return -1;
            }
            return data[pos++] & 0xff;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (pos >= data.length) {
                return -1;
            }
            int toRead = Math.min(len, data.length - pos);
            System.arraycopy(data, pos, b, off, toRead);
            pos += toRead;
            return toRead;
        }
    }

    // A minimal Cipher subclass to allow construction of CipherInputStream
    static class NoOpCipher extends Cipher {
        protected NoOpCipher() {
            super(null, null, null);
        }
    }

    public static void main(String[] args) throws Exception {
        testReadReturnsMinusOneOnEmptyStream();
        testReadReturnsByteFromStream();
    }

    // Test that read() returns -1 on empty underlying stream
    private static void testReadReturnsMinusOneOnEmptyStream() throws IOException {
        InputStream emptyStream = new FixedInputStream(new byte[0]);
        Cipher dummyCipher = new NoOpCipher();
        CipherInputStream cis = new CipherInputStream(emptyStream, dummyCipher);

        int result = cis.read();
        if (result != -1) {
            throw new RuntimeException("Expected -1 from read() on empty stream, got: " + result);
        }
    }

    // Test that read() returns the first byte from underlying stream
    private static void testReadReturnsByteFromStream() throws IOException {
        byte[] data = new byte[] { (byte)0xAB, (byte)0xCD };
        InputStream fixedStream = new FixedInputStream(data);
        Cipher dummyCipher = new NoOpCipher();
        CipherInputStream cis = new CipherInputStream(fixedStream, dummyCipher);

        int firstByte = cis.read();
        if (firstByte != (data[0] & 0xff)) {
            throw new RuntimeException("Expected first byte " + (data[0] & 0xff) + ", got " + firstByte);
        }

        int secondByte = cis.read();
        if (secondByte != (data[1] & 0xff)) {
            throw new RuntimeException("Expected second byte " + (data[1] & 0xff) + ", got " + secondByte);
        }
    }
}
