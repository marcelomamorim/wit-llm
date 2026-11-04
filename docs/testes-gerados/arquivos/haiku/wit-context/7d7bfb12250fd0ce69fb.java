/*
 * @test
 * @summary Witup-generated checks for Inflater.setInput(ByteBuffer): null rejection and observable inflation from a ByteBuffer
 * @run main InflaterWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class InflaterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testHeapBufferInflatesOriginalData();
        testBufferWithNonZeroPositionInflatesOriginalData();
    }

    static void testNullInputThrowsNPE() {
        Inflater inf = new Inflater();
        try {
            inf.setInput((ByteBuffer) null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            inf.end();
        }
    }

    static void testHeapBufferInflatesOriginalData() throws Exception {
        byte[] original = "witup-inflater-heap-buffer".getBytes(StandardCharsets.UTF_8);
        byte[] compressed = compress(original);

        Inflater inf = new Inflater();
        try {
            inf.setInput(ByteBuffer.wrap(compressed));
            byte[] out = new byte[original.length + 16];
            int n = inf.inflate(out);
            if (n != original.length) {
                throw new RuntimeException("Unexpected inflated length: " + n);
            }
            if (!Arrays.equals(Arrays.copyOf(out, n), original)) {
                throw new RuntimeException("Inflated data mismatch");
            }
            if (!inf.finished()) {
                throw new RuntimeException("Inflater should be finished");
            }
        } finally {
            inf.end();
        }
    }

    static void testBufferWithNonZeroPositionInflatesOriginalData() throws Exception {
        byte[] original = "witup-inflater-positioned".getBytes(StandardCharsets.UTF_8);
        byte[] compressed = compress(original);

        int offset = 5;
        ByteBuffer buf = ByteBuffer.allocate(offset + compressed.length + 3);
        buf.position(offset);
        buf.put(compressed);
        buf.flip();
        buf.position(offset);

        Inflater inf = new Inflater();
        try {
            inf.setInput(buf);
            byte[] out = new byte[original.length + 16];
            int n = inf.inflate(out);
            if (n != original.length) {
                throw new RuntimeException("Unexpected inflated length: " + n);
            }
            if (!Arrays.equals(Arrays.copyOf(out, n), original)) {
                throw new RuntimeException("Inflated data mismatch with positioned buffer");
            }
        } finally {
            inf.end();
        }
    }

    static byte[] compress(byte[] data) {
        Deflater def = new Deflater();
        try {
            def.setInput(data);
            def.finish();
            byte[] buf = new byte[256];
            int len = def.deflate(buf);
            return Arrays.copyOf(buf, len);
        } finally {
            def.end();
        }
    }
}
