/*
 * @test
 * @summary Test java.nio.channels.Channels.newWriter(WritableByteChannel, Charset)
 * @run main ChannelsWitupTest
 */

import java.io.Writer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ChannelsWitupTest {

    static class DummyWritableByteChannel implements WritableByteChannel {
        private boolean open = true;
        public int write(java.nio.ByteBuffer src) {
            // pretend to write all bytes
            int remaining = src.remaining();
            src.position(src.position() + remaining);
            return remaining;
        }
        public boolean isOpen() { return open; }
        public void close() { open = false; }
    }

    public static void main(String[] args) throws Exception {
        // Test that passing null charset throws NullPointerException
        try {
            Channels.newWriter(new DummyWritableByteChannel(), null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException npe) {
            if (!"charset".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        }

        // Test that newWriter returns a non-null Writer
        WritableByteChannel channel = new DummyWritableByteChannel();
        Charset charset = StandardCharsets.UTF_8;
        Writer writer = Channels.newWriter(channel, charset);
        if (writer == null) {
            throw new RuntimeException("newWriter returned null Writer");
        }

        // Test that the Writer can write and flush without exception
        writer.write("test");
        writer.flush();
        writer.close();

        // Test that newWriter with a different charset returns a Writer
        Charset isoCharset = Charset.forName("ISO-8859-1");
        Writer writer2 = Channels.newWriter(channel, isoCharset);
        if (writer2 == null) {
            throw new RuntimeException("newWriter returned null Writer for ISO-8859-1 charset");
        }
        writer2.close();
    }
}
