/*
 * @test
 * @summary Verify Inflater.setInput(ByteBuffer) inflates heap and direct buffers and rejects null
 * @run main InflaterWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class InflaterWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] original = "hello hello hello inflater".getBytes(StandardCharsets.UTF_8);
        byte[] compressed = compress(original);

        testHeapBuffer(original, compressed);
        testDirectBuffer(original, compressed);
        testNullRejected();
    }

    static byte[] compress(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (!d.finished()) {
            int n = d.deflate(buf);
            bos.write(buf, 0, n);
        }
        d.end();
        return bos.toByteArray();
    }

    static byte[] inflateAll(Inflater inf, int maxSize) throws DataFormatException {
        byte[] out = new byte[maxSize];
        int n = inf.inflate(out);
        return Arrays.copyOf(out, n);
    }

    static void testHeapBuffer(byte[] original, byte[] compressed) throws DataFormatException {
        Inflater inf = new Inflater();
        try {
            inf.setInput(ByteBuffer.wrap(compressed));
            byte[] result = inflateAll(inf, original.length + 16);
            if (!Arrays.equals(original, result)) {
                throw new RuntimeException("heap buffer: inflated data mismatch");
            }
        } finally {
            inf.end();
        }
    }

    static void testDirectBuffer(byte[] original, byte[] compressed) throws DataFormatException {
        ByteBuffer direct = ByteBuffer.allocateDirect(compressed.length);
        direct.put(compressed);
        direct.flip();
        Inflater inf = new Inflater();
        try {
            inf.setInput(direct);
            byte[] result = inflateAll(inf, original.length + 16);
            if (!Arrays.equals(original, result)) {
                throw new RuntimeException("direct buffer: inflated data mismatch");
            }
        } finally {
            inf.end();
        }
    }

    static void testNullRejected() {
        Inflater inf = new Inflater();
        try {
            inf.setInput((ByteBuffer) null);
            throw new RuntimeException("expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            inf.end();
        }
    }
}
