/*
 * @test
 * @summary Checks InflaterInputStream.available() return values and the IOException raised after close
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] comp = compress("hello witup".getBytes("UTF-8"));
        testAvailableBeforeEof(comp);
        testAvailableAfterClose(comp);
        testAvailableAfterEof(comp);
    }

    static byte[] compress(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        byte[] out = new byte[512];
        int n = d.deflate(out);
        d.end();
        byte[] result = new byte[n];
        System.arraycopy(out, 0, result, 0, n);
        return result;
    }

    static void testAvailableBeforeEof(byte[] comp) throws IOException {
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp));
        int avail = in.available();
        in.close();
        if (avail != 1) {
            throw new RuntimeException("expected available()==1 before EOF, got " + avail);
        }
    }

    static void testAvailableAfterClose(byte[] comp) throws IOException {
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp), new Inflater());
        in.close();
        try {
            in.available();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException from available() after close");
    }

    static void testAvailableAfterEof(byte[] comp) throws IOException {
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp));
        while (in.read() != -1) {
            // drain the decompressed data
        }
        int avail = in.available();
        in.close();
        if (avail != 0) {
            throw new RuntimeException("expected available()==0 after EOF, got " + avail);
        }
    }
}
