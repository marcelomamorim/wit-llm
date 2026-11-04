/*
 * @test
 * @summary WITUP-derived checks for DeflaterInputStream(InputStream) null input and round trip
 * @run main DeflaterInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testRoundTrip();
    }

    static void testNullInputThrowsNPE() {
        try {
            new DeflaterInputStream(null);
            throw new RuntimeException("NullPointerException not thrown");
        } catch (NullPointerException expected) {
        }
    }

    static void testRoundTrip() throws Exception {
        byte[] data = "hello hello hello deflate".getBytes("UTF-8");
        InputStream in = new DeflaterInputStream(new ByteArrayInputStream(data));
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        byte[] tmp = new byte[64];
        int n;
        while ((n = in.read(tmp)) != -1) {
            compressed.write(tmp, 0, n);
        }
        in.close();

        Inflater inf = new Inflater();
        inf.setInput(compressed.toByteArray());
        byte[] result = new byte[data.length];
        int total = 0;
        while (total < data.length && !inf.finished()) {
            int r = inf.inflate(result, total, result.length - total);
            if (r == 0 && (inf.needsInput() || inf.needsDictionary())) {
                break;
            }
            total += r;
        }
        inf.end();

        if (total != data.length || !Arrays.equals(result, data)) {
            throw new RuntimeException("Round trip mismatch");
        }
    }
}
