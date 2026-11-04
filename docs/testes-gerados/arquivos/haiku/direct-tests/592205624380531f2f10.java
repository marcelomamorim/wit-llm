/*
 * @test
 * @summary Tests Channels.newChannel(OutputStream) for null rejection, FileOutputStream channel reuse, and generic stream wrapping with close propagation
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;

public class ChannelsWitupTest {

    static class RecordingStream extends OutputStream {
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        boolean closed = false;

        @Override
        public void write(int b) throws IOException {
            data.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            data.write(b, off, len);
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullRejected() {
        try {
            Channels.newChannel((OutputStream) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGenericStreamWrite() throws IOException {
        RecordingStream rs = new RecordingStream();
        WritableByteChannel ch = Channels.newChannel(rs);
        check(ch != null, "channel must not be null");
        check(ch.isOpen(), "new channel must be open");

        byte[] bytes = "hello".getBytes("US-ASCII");
        int n = ch.write(ByteBuffer.wrap(bytes));
        check(n == bytes.length, "write must report all bytes written, got " + n);
        check("hello".equals(new String(rs.data.toByteArray(), "US-ASCII")),
              "stream content mismatch");

        ch.close();
        check(!ch.isOpen(), "channel must be closed after close()");
        check(rs.closed, "closing the channel must close the underlying stream");
    }

    static void testFileOutputStreamChannel() throws IOException {
        File f = File.createTempFile("channels-witup", ".bin");
        f.deleteOnExit();
        try {
            FileOutputStream fos = new FileOutputStream(f);
            WritableByteChannel ch;
            try {
                ch = Channels.newChannel(fos);
                check(ch instanceof FileChannel, "FileOutputStream must yield a FileChannel");
                byte[] bytes = "file-data".getBytes("US-ASCII");
                int n = ch.write(ByteBuffer.wrap(bytes));
                check(n == bytes.length, "file channel write count mismatch");
            } finally {
                fos.close();
            }
            try (InputStream in = new FileInputStream(f)) {
                byte[] buf = new byte[64];
                int r = in.read(buf);
                String s = new String(buf, 0, r, "US-ASCII");
                check("file-data".equals(s), "file content mismatch: " + s);
            }
        } finally {
            f.delete();
        }
    }

    public static void main(String[] args) throws Exception {
        testNullRejected();
        testGenericStreamWrite();
        testFileOutputStreamChannel();
    }
}
