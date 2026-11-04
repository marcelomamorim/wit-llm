/*
 * @test
 * @summary Test java.nio.channels.Channels.newReader(ReadableByteChannel, Charset)
 * @run main ChannelsWitupTest
 */

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.io.Reader;
import java.util.Objects;

public class ChannelsWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharset();
        testValidCharset();
    }

    private static void testNullCharset() {
        ReadableByteChannel dummyChannel = new ReadableByteChannel() {
            public int read(java.nio.ByteBuffer dst) { return -1; }
            public boolean isOpen() { return true; }
            public void close() { }
        };
        try {
            Channels.newReader(dummyChannel, null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException npe) {
            if (!"charset".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testValidCharset() throws Exception {
        ReadableByteChannel dummyChannel = new ReadableByteChannel() {
            public int read(java.nio.ByteBuffer dst) { return -1; }
            public boolean isOpen() { return true; }
            public void close() { }
        };
        Charset charset = Charset.forName("UTF-8");
        Reader r = Channels.newReader(dummyChannel, charset);
        if (r == null) {
            throw new RuntimeException("newReader returned null Reader");
        }
        // The returned Reader should be open and not null
        r.close();
    }
}
