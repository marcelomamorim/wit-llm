/*
 * @test
 * @summary Test sun.security.ssl.SSLSocketImpl.write(int) method
 * @run main SSLSocketImplWitupTest
 */

import sun.security.ssl.SSLSocketImpl;
import java.io.IOException;

public class SSLSocketImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass to test write(int) since SSLSocketImpl has no public constructor
        SSLSocketImpl socket = new SSLSocketImpl(null, null, null, false);

        // Test writing a single byte - should delegate to write(byte[], int, int)
        // We override write(byte[], int, int) to verify it is called with correct parameters
        TestSSLSocketImpl testSocket = new TestSSLSocketImpl();
        testSocket.write(0x41); // ASCII 'A'
        if (testSocket.lastWrittenByte != (byte)0x41) {
            throw new AssertionError("write(int) did not write the correct byte");
        }

        // Test writing a negative byte value
        testSocket.write(-1);
        if (testSocket.lastWrittenByte != (byte)-1) {
            throw new AssertionError("write(int) did not write the correct negative byte");
        }
    }

    // Minimal subclass to intercept write(byte[], int, int) calls
    static class TestSSLSocketImpl extends SSLSocketImpl {
        byte lastWrittenByte = 0;

        TestSSLSocketImpl() {
            super(null, null, null, false);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            if (len != 1) {
                throw new IOException("Expected length 1");
            }
            lastWrittenByte = b[off];
        }
    }
}
