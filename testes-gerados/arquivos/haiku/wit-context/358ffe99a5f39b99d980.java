/*
 * @test
 * @summary Witup-generated checks for DeflaterOutputStream(OutputStream, Deflater, int)
 * @run main DeflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

public class DeflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOutputThrowsNpe();
        testNullDeflaterThrowsNpe();
        testNonPositiveSizeThrowsIae();
        testValidRoundTrip();
    }

    interface Action {
        void run() throws Exception;
    }

    static void expectThrows(Class<? extends Throwable> expected, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new AssertionError("unexpected exception: " + t, t);
        }
        throw new AssertionError("no exception thrown, expected " + expected.getName());
    }

    static void testNullOutputThrowsNpe() {
        expectThrows(NullPointerException.class,
            () -> new DeflaterOutputStream(null, new Deflater(), 16));
    }

    static void testNullDeflaterThrowsNpe() {
        expectThrows(NullPointerException.class,
            () -> new DeflaterOutputStream(new ByteArrayOutputStream(), null, 16));
    }

    static void testNonPositiveSizeThrowsIae() {
        expectThrows(IllegalArgumentException.class,
            () -> new DeflaterOutputStream(new ByteArrayOutputStream(), new Deflater(), 0));
        expectThrows(IllegalArgumentException.class,
            () -> new DeflaterOutputStream(new ByteArrayOutputStream(), new Deflater(), -1));
    }

    static void testValidRoundTrip() throws Exception {
        byte[] data = "hello hello hello hello".getBytes(StandardCharsets.US_ASCII);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Deflater def = new Deflater();
        DeflaterOutputStream dos = new DeflaterOutputStream(bos, def, 8);
        dos.write(data);
        dos.close();
        def.end();

        byte[] compressed = bos.toByteArray();
        Inflater inf = new Inflater();
        inf.setInput(compressed);
        byte[] result = new byte[data.length];
        int n = inf.inflate(result);
        boolean finished = inf.finished();
        inf.end();

        if (n != data.length || !finished || !Arrays.equals(result, data)) {
            throw new AssertionError("round trip mismatch: n=" + n + " finished=" + finished);
        }
    }
}
