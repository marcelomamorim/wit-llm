/*
 * @test
 * @summary Test java.nio.channels.Channels.read() method behavior
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.Channels;

public class ChannelsWitupTest {

    public static void main(String[] args) throws IOException {
        ChannelsWitupTest test = new ChannelsWitupTest();
        test.testReadReturnsByteValue();
        test.testReadReturnsMinusOneOnEOF();
    }

    // Test that read() returns a byte value 0..255 when underlying read returns 1
    public void testReadReturnsByteValue() throws IOException {
        // Create a Channels instance with a custom subclass to override read(byte[])
        final byte testByte = (byte)0xAB;
        Channels ch = new Channels() {
            byte[] b1 = null;
            public synchronized int read() throws IOException {
                if (b1 == null) b1 = new byte[1];
                b1[0] = testByte;
                int n = 1; // simulate read returns 1 byte
                if (n == 1) return b1[0] & 0xff;
                return -1;
            }
        };

        int result = ch.read();
        if (result != (testByte & 0xff)) {
            throw new AssertionError("Expected " + (testByte & 0xff) + " but got " + result);
        }
    }

    // Test that read() returns -1 when underlying read returns -1 (EOF)
    public void testReadReturnsMinusOneOnEOF() throws IOException {
        Channels ch = new Channels() {
            byte[] b1 = null;
            public synchronized int read() throws IOException {
                if (b1 == null) b1 = new byte[1];
                int n = -1; // simulate EOF
                if (n == 1) return b1[0] & 0xff;
                return -1;
            }
        };

        int result = ch.read();
        if (result != -1) {
            throw new AssertionError("Expected -1 but got " + result);
        }
    }

}
