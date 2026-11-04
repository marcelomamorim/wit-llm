/*
 * @test
 * @summary Tests the public GZIPInputStream(InputStream in) constructor, which delegates to GZIPInputStream(in, 512)
 * @run main GZIPInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;

public class GZIPInputStreamWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static byte[] gzip(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (GZIPOutputStream gos = new GZIPOutputStream(bos)) {
            gos.write(data);
        }
        return bos.toByteArray();
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[64];
        int n;
        while ((n = in.read(buf, 0, buf.length)) != -1) {
            out.write(buf, 0, n);
        }
        return out.toByteArray();
    }

    static void testRoundTrip() throws Exception {
        byte[] original = "hello gzip constructor".getBytes(StandardCharsets.UTF_8);
        byte[] compressed = gzip(original);
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed))) {
            byte[] result = readAll(gis);
            check(java.util.Arrays.equals(original, result), "round trip mismatch");
        }
    }

    static void testEquivalentToSizedConstructor() throws Exception {
        byte[] original = new byte[2000];
        for (int i = 0; i < original.length; i++) {
            original[i] = (byte) (i * 31);
        }
        byte[] compressed = gzip(original);
        byte[] viaDefault;
        byte[] viaSized;
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed))) {
            viaDefault = readAll(gis);
        }
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed), 512)) {
            viaSized = readAll(gis);
        }
        check(java.util.Arrays.equals(original, viaDefault), "default constructor output mismatch");
        check(java.util.Arrays.equals(viaDefault, viaSized), "default and size 512 outputs differ");
    }

    static void testEmptyPayload() throws Exception {
        byte[] compressed = gzip(new byte[0]);
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed))) {
            int r = gis.read();
            check(r == -1, "expected EOF for empty payload, got " + r);
        }
    }

    static void testNonGzipHeaderRejected() throws Exception {
        byte[] bogus = "not a gzip stream at all".getBytes(StandardCharsets.UTF_8);
        try {
            new GZIPInputStream(new ByteArrayInputStream(bogus));
            throw new RuntimeException("expected ZipException for invalid header");
        } catch (ZipException expected) {
            // expected
        }
    }

    static void testEmptyInputRejected() throws Exception {
        try {
            new GZIPInputStream(new ByteArrayInputStream(new byte[0]));
            throw new RuntimeException("expected EOFException for empty input");
        } catch (EOFException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        testRoundTrip();
        testEquivalentToSizedConstructor();
        testEmptyPayload();
        testNonGzipHeaderRejected();
        testEmptyInputRejected();
    }
}
