/*
 * @test
 * @summary Tests InflaterOutputStream.flush() observable behavior: full flush, flush on fresh stream, partial flush, small buffer, and flush after close
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] original = "hello inflater flush test hello inflater flush test".getBytes("UTF-8");
        byte[] compressed = compress(original);

        // 1: writing all compressed data then flushing yields the full original output
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(bos);
        ios.write(compressed);
        ios.flush();
        check(Arrays.equals(original, bos.toByteArray()), "full data flush");
        ios.close();

        // 2: flush on a fresh stream with no input writes nothing and does not fail
        bos = new ByteArrayOutputStream();
        ios = new InflaterOutputStream(bos, new Inflater(), 16);
        ios.flush();
        check(bos.size() == 0, "flush on fresh stream");
        ios.close();

        // 3: flush after partial input yields a prefix of the original; completing input restores full output
        bos = new ByteArrayOutputStream();
        ios = new InflaterOutputStream(bos);
        int half = compressed.length / 2;
        ios.write(compressed, 0, half);
        ios.flush();
        byte[] partial = bos.toByteArray();
        check(partial.length <= original.length, "partial flush does not exceed original");
        check(Arrays.equals(Arrays.copyOf(original, partial.length), partial), "partial flush is prefix");
        ios.write(compressed, half, compressed.length - half);
        ios.flush();
        check(Arrays.equals(original, bos.toByteArray()), "flush after completing input");
        ios.close();

        // 4: small decompression buffer still produces full output on flush
        bos = new ByteArrayOutputStream();
        ios = new InflaterOutputStream(bos, new Inflater(), 1);
        ios.write(compressed);
        ios.flush();
        check(Arrays.equals(original, bos.toByteArray()), "flush with bufLen 1");
        ios.close();

        // 5: flush after close throws IOException with the documented message
        ios = new InflaterOutputStream(new ByteArrayOutputStream());
        ios.close();
        boolean thrown = false;
        try {
            ios.flush();
        } catch (IOException e) {
            thrown = true;
            check("Stream closed".equals(e.getMessage()), "closed message");
        }
        check(thrown, "flush after close must throw");
    }

    private static byte[] compress(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] tmp = new byte[64];
        while (!d.finished()) {
            int n = d.deflate(tmp);
            out.write(tmp, 0, n);
        }
        d.end();
        return out.toByteArray();
    }

    private static void check(boolean cond, String what) {
        if (!cond) {
            throw new RuntimeException("Failed: " + what);
        }
    }
}
