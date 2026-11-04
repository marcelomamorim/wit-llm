/*
 * @test
 * @summary Test the write(int b) method in java.nio.channels.Channels
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.Channels;

public class ChannelsWitupTest {

    public static void main(String[] args) {
        testWriteMethod();
    }

    public static void testWriteMethod() {
        try {
            // Create a dummy implementation of WritableByteChannel for testing
            DummyWritableByteChannel channel = new DummyWritableByteChannel();
            // Create an instance of the class containing the method
            // Since write(int b) is static in Channels, call directly
            // Test with a normal byte value
            Channels.write(65); // ASCII 'A'
            // Verify that the dummy channel received the byte
            byte[] received = channel.getWrittenBytes();
            if (received.length != 1 || received[0] != 65) {
                throw new AssertionError("Failed to write byte 65");
            }
            // Test with a byte value outside ASCII range
            Channels.write(200);
            received = channel.getWrittenBytes();
            if (received.length != 1 || received[0] != (byte)200) {
                throw new AssertionError("Failed to write byte 200");
            }
            // Test with multiple writes
            Channels.write(10); // newline
            received = channel.getWrittenBytes();
            if (received.length != 1 || received[0] != 10) {
                throw new AssertionError("Failed to write byte 10");
            }
            System.out.println("All tests passed.");
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred during test", e);
        }
    }

    // Dummy implementation of WritableByteChannel for testing purposes
    static class DummyWritableByteChannel implements java.nio.channels.WritableByteChannel {
        private byte[] buffer = new byte[0];
        private byte[] tempBuffer = new byte[1];

        @Override
        public int write(java.nio.ByteBuffer src) throws IOException {
            int remaining = src.remaining();
            byte[] bytes = new byte[remaining];
            src.get(bytes);
            // Append to buffer
            byte[] newBuffer = new byte[buffer.length + remaining];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            System.arraycopy(bytes, 0, newBuffer, buffer.length, remaining);
            buffer = newBuffer;
            return remaining;
        }

        @Override
        public void close() throws IOException {
            // No-op
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        public byte[] getWrittenBytes() {
            return buffer;
        }
    }
}
