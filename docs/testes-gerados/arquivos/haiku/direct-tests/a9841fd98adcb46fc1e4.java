/*
 * @test
 * @summary Basic observable behavior of Channels.newWriter(WritableByteChannel, Charset)
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testUtf8Output();
        testLatin1Output();
        testCloseClosesChannel();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullCharsetThrowsNPE() throws Exception {
        WritableByteChannel ch = Channels.newChannel(new ByteArrayOutputStream());
        boolean thrown = false;
        try {
            Channels.newWriter(ch, null);
        } catch (NullPointerException e) {
            thrown = true;
            check("charset".equals(e.getMessage()), "unexpected NPE message: " + e.getMessage());
        }
        check(thrown, "expected NullPointerException for null charset");
    }

    static void testUtf8Output() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(bos);
        Writer w = Channels.newWriter(ch, StandardCharsets.UTF_8);
        w.write("h\u00e9llo");
        w.flush();
        byte[] expected = "h\u00e9llo".getBytes(StandardCharsets.UTF_8);
        check(Arrays.equals(bos.toByteArray(), expected), "UTF-8 output mismatch");
        w.close();
    }

    static void testLatin1Output() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(bos);
        Writer w = Channels.newWriter(ch, StandardCharsets.ISO_8859_1);
        w.write("\u00e9");
        w.flush();
        check(Arrays.equals(bos.toByteArray(), new byte[] { (byte) 0xE9 }),
              "ISO-8859-1 output mismatch");
        w.close();
    }

    static void testCloseClosesChannel() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(bos);
        Writer w = Channels.newWriter(ch, StandardCharsets.UTF_8);
        check(ch.isOpen(), "channel should be open before close");
        w.write("abc");
        w.close();
        check(!ch.isOpen(), "channel should be closed after writer close");
        check(Arrays.equals(bos.toByteArray(), "abc".getBytes(StandardCharsets.UTF_8)),
              "close should flush pending bytes");
    }
}
