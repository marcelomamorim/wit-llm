/*
 * @test
 * @summary Test java.nio.channels.Channels.newChannel(OutputStream)
 * @run main ChannelsWitupTest
 */

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;
import java.io.IOException;

public class ChannelsWitupTest {

    public static void main(String[] args) throws IOException {
        testNewChannelWithFileOutputStream();
        testNewChannelWithCustomOutputStream();
    }

    static void testNewChannelWithFileOutputStream() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("testfile.txt")) {
            WritableByteChannel channel = Channels.newChannel(fos);
            String data = "Hello, World!";
            byte[] bytes = data.getBytes();
            channel.write(java.nio.ByteBuffer.wrap(bytes));
        }
    }

    static void testNewChannelWithCustomOutputStream() throws IOException {
        OutputStream customOut = new OutputStream() {
            private byte[] buffer = new byte[1024];
            private int count = 0;

            @Override
            public void write(int b) {
                if (count < buffer.length) {
                    buffer[count++] = (byte) b;
                }
            }
        };
        WritableByteChannel channel = Channels.newChannel(customOut);
        String data = "Custom Stream Data";
        channel.write(java.nio.ByteBuffer.wrap(data.getBytes()));
    }
}
