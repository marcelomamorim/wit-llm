/*
 * @test
 * @summary Witup-derived tests for InflaterInputStream(InputStream, Inflater) null checks and decompression
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] original = "witup inflater test data".getBytes("UTF-8");
        byte[] compressed = deflate(original);

        try (InflaterInputStream in = new InflaterInputStream(
                new ByteArrayInputStream(compressed), new Inflater())) {
            byte[] result = readAll(in);
            if (!Arrays.equals(original, result)) {
                throw new RuntimeException("Decompressed data mismatch (2-arg constructor)");
            }
        }

        try (InflaterInputStream in = new InflaterInputStream(
                new ByteArrayInputStream(compressed))) {
            byte[] result = readAll(in);
            if (!Arrays.equals(original, result)) {
                throw new RuntimeException("Decompressed data mismatch (1-arg constructor)");
            }
        }

        boolean npeForNullInput = false;
        try {
            new InflaterInputStream(null, new Inflater());
        } catch (NullPointerException e) {
            npeForNullInput = true;
        }
        if (!npeForNullInput) {
            throw new RuntimeException("Expected NullPointerException when input stream is null");
        }

        boolean npeForNullInflater = false;
        try {
            new InflaterInputStream(new ByteArrayInputStream(compressed), null);
        } catch (NullPointerException e) {
            npeForNullInflater = true;
        }
        if (!npeForNullInflater) {
            throw new RuntimeException("Expected NullPointerException when inflater is null");
        }
    }

    private static byte[] deflate(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[64];
        while (!d.finished()) {
            int n = d.deflate(buf);
            bos.write(buf, 0, n);
        }
        d.end();
        return bos.toByteArray();
    }

    private static byte[] readAll(InflaterInputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            bos.write(b);
        }
        return bos.toByteArray();
    }
}
