/*
 * @test
 * @summary WITUP-derived checks for Channels.newChannel(OutputStream)
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullOutputThrowsNpe();
        testFileOutputStreamReturnsItsChannel();
        testGenericStreamWritesBytes();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullOutputThrowsNpe() {
        try {
            Channels.newChannel((OutputStream) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null out");
    }

    static void testFileOutputStreamReturnsItsChannel() throws Exception {
        File f = File.createTempFile("witup", ".tmp");
        try {
            try (FileOutputStream fos = new FileOutputStream(f)) {
                WritableByteChannel ch = Channels.newChannel(fos);
                check(ch != null, "channel must not be null");
                check(ch == fos.getChannel(), "FileOutputStream must yield its own channel");
            }
        } finally {
            f.delete();
        }
    }

    static void testGenericStreamWritesBytes() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(baos);
        check(ch != null, "channel must not be null");
        check(ch.isOpen(), "new channel must be open");
        int n = ch.write(ByteBuffer.wrap("abc".getBytes("US-ASCII")));
        check(n == 3, "expected 3 bytes written, got " + n);
        check("abc".equals(baos.toString("US-ASCII")), "stream content mismatch");
        ch.close();
        check(!ch.isOpen(), "channel must be closed after close()");
    }
}
