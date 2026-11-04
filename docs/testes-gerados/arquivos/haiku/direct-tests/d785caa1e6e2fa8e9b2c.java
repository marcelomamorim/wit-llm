/*
 * @test
 * @summary Verifies single-byte read() of InflaterInputStream returns uncompressed data and fails after close
 * @run main InflaterInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] text = "hello inflater read()".getBytes(StandardCharsets.US_ASCII);
        checkRoundTrip(text, new InflaterInputStream(new ByteArrayInputStream(deflate(text))));

        checkRoundTrip(text, new InflaterInputStream(
                new ByteArrayInputStream(deflate(text)), new Inflater(), 1));

        byte[] all = new byte[256];
        for (int i = 0; i < all.length; i++) {
            all[i] = (byte) i;
        }
        checkRoundTrip(all, new InflaterInputStream(new ByteArrayInputStream(deflate(all))));

        byte[] empty = new byte[0];
        checkRoundTrip(empty, new InflaterInputStream(new ByteArrayInputStream(deflate(empty))));

        checkClosedThrows();
    }

    static byte[] deflate(byte[] input) {
        Deflater d = new Deflater();
        d.setInput(input);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] tmp = new byte[64];
        while (!d.finished()) {
            int n = d.deflate(tmp);
            bos.write(tmp, 0, n);
        }
        d.end();
        return bos.toByteArray();
    }

    static void checkRoundTrip(byte[] expected, InputStream in) throws IOException {
        try {
            for (int i = 0; i < expected.length; i++) {
                int b = in.read();
                if (b != (expected[i] & 0xFF)) {
                    throw new RuntimeException("mismatch at " + i + ": " + b);
                }
            }
            if (in.read() != -1) {
                throw new RuntimeException("expected -1 at end of stream");
            }
            if (in.read() != -1) {
                throw new RuntimeException("expected -1 on repeated read at end");
            }
        } finally {
            in.close();
        }
    }

    static void checkClosedThrows() throws IOException {
        InflaterInputStream in = new InflaterInputStream(
                new ByteArrayInputStream(deflate(new byte[] {1, 2, 3})));
        in.close();
        try {
            in.read();
            throw new RuntimeException("expected IOException after close");
        } catch (IOException expected) {
            // expected: ensureOpen() reports closed stream
        }
    }
}
