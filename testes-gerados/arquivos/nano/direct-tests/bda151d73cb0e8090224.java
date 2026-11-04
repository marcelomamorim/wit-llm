/*
 * @test
 * @summary Test the read() method of java.nio.channels.Channels class.
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.Channels;

public class ChannelsWitupTest {

    public static void main(String[] args) {
        testReadReturnsByteValue();
        testReadReturnsMinusOneOnEOF();
    }

    private static void testReadReturnsByteValue() {
        class MockReadableChannel extends java.nio.channels.ReadableByteChannel {
            private boolean readCalled = false;
            @Override
            public int read(java.nio.ByteBuffer dst) throws IOException {
                if (readCalled) {
                    return -1;
                } else {
                    dst.put((byte) 42); // arbitrary byte
                    readCalled = true;
                    return 1;
                }
            }
            @Override
            public boolean isOpen() {
                return true;
            }
            @Override
            public void close() throws IOException {
                // do nothing
            }
        }
        MockReadableChannel mockChannel = new MockReadableChannel();
        try {
            int result = Channels.read(mockChannel);
            assert result == 42 : "Expected 42, got " + result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testReadReturnsMinusOneOnEOF() {
        class EOFChannel extends java.nio.channels.ReadableByteChannel {
            @Override
            public int read(java.nio.ByteBuffer dst) throws IOException {
                return -1; // simulate EOF
            }
            @Override
            public boolean isOpen() {
                return true;
            }
            @Override
            public void close() throws IOException {
                // do nothing
            }
        }
        EOFChannel eofChannel = new EOFChannel();
        try {
            int result = Channels.read(eofChannel);
            assert result == -1 : "Expected -1 for EOF, got " + result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
