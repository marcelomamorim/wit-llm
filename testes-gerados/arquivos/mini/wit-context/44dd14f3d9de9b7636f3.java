/*
 * @test
 * @summary Test java.nio.channels.Channels.newInputStream method
 * @run main ChannelsWitupTest
 */

import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Objects;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNewInputStreamWithNullChannel();
        testNewInputStreamWithDummyChannel();
    }

    private static void testNewInputStreamWithNullChannel() {
        try {
            Channels.newInputStream(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
            if (!"ch".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testNewInputStreamWithDummyChannel() throws Exception {
        ReadableByteChannel dummyChannel = new ReadableByteChannel() {
            private boolean open = true;
            public int read(java.nio.ByteBuffer dst) {
                return -1; // simulate end-of-stream
            }
            public boolean isOpen() {
                return open;
            }
            public void close() {
                open = false;
            }
        };

        InputStream is = Channels.newInputStream(dummyChannel);
        if (is == null) {
            throw new RuntimeException("newInputStream returned null");
        }
        int readResult = is.read();
        if (readResult != -1) {
            throw new RuntimeException("Expected end-of-stream (-1) but got " + readResult);
        }
        is.close();
    }
}
