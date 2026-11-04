/*
 * @test
 * @summary Witup-style tests for Channels.newChannel(InputStream) observable behavior
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testByteArrayStreamReadsData();
        testEndOfStreamReturnsMinusOne();
        testCloseClosesChannel();
        testFileInputStreamReturnsItsChannel();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullInputThrowsNPE() {
        try {
            Channels.newChannel((InputStream) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testByteArrayStreamReadsData() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5});
        ReadableByteChannel ch = Channels.newChannel(in);
        check(ch.isOpen(), "channel should be open");
        ByteBuffer bb = ByteBuffer.allocate(16);
        int n = ch.read(bb);
        check(n == 5, "expected 5 bytes read but was " + n);
        check(bb.get(0) == 1, "byte 0 mismatch");
        check(bb.get(4) == 5, "byte 4 mismatch");
        ch.close();
    }

    static void testEndOfStreamReturnsMinusOne() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[] {42});
        ReadableByteChannel ch = Channels.newChannel(in);
        ByteBuffer bb = ByteBuffer.allocate(4);
        int first = ch.read(bb);
        check(first == 1, "expected 1 byte but was " + first);
        int second = ch.read(ByteBuffer.allocate(4));
        check(second == -1, "expected -1 at end of stream but was " + second);
        ch.close();
    }

    static void testCloseClosesChannel() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[] {1, 2});
        ReadableByteChannel ch = Channels.newChannel(in);
        check(ch.isOpen(), "channel should be open before close");
        ch.close();
        check(!ch.isOpen(), "channel should be closed after close()");
    }

    static void testFileInputStreamReturnsItsChannel() throws IOException {
        File f = File.createTempFile("channels_witup", ".bin");
        try {
            try (FileOutputStream fos = new FileOutputStream(f)) {
                fos.write(new byte[] {9, 8, 7});
            }
            FileInputStream fis = new FileInputStream(f);
            try {
                ReadableByteChannel ch = Channels.newChannel(fis);
                check(ch == fis.getChannel(), "newChannel should return the FileInputStream's channel");
                ByteBuffer bb = ByteBuffer.allocate(8);
                int n = ch.read(bb);
                check(n == 3, "expected 3 bytes from file channel but was " + n);
            } finally {
                fis.close();
            }
        } finally {
            f.delete();
        }
    }
}
