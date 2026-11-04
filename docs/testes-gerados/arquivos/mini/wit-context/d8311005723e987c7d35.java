/*
 * @test
 * @summary Test java.nio.channels.Channels.newChannel(InputStream) for null input and normal behavior
 * @run main ChannelsWitupTest
 */

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Channels;

public class ChannelsWitupTest {
    public static void main(String[] args) throws Exception {
        testNewChannelNullInput();
        testNewChannelFileInputStream();
        testNewChannelOtherInputStream();
    }

    private static void testNewChannelNullInput() {
        try {
            Channels.newChannel(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNewChannelFileInputStream() throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(java.io.File.createTempFile("ChannelsWitupTest", ".tmp"));
            ReadableByteChannel channel = Channels.newChannel(fis);
            if (channel == null) {
                throw new RuntimeException("newChannel returned null for FileInputStream");
            }
            if (channel != fis.getChannel()) {
                throw new RuntimeException("newChannel did not return the FileInputStream's channel");
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    private static void testNewChannelOtherInputStream() throws Exception {
        InputStream in = new InputStream() {
            @Override
            public int read() {
                return -1; // EOF
            }
        };
        ReadableByteChannel channel = Channels.newChannel(in);
        if (channel == null) {
            throw new RuntimeException("newChannel returned null for generic InputStream");
        }
        // We cannot assert more about the implementation class, just that it is non-null
    }
}
