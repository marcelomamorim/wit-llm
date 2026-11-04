/*
 * @test
 * @summary Tests argument validation and observable inflation behavior of Inflater.setInput(byte[], int, int)
 * @run main InflaterWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class InflaterWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = "blahblahblah".getBytes(StandardCharsets.UTF_8);
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        byte[] comp = new byte[100];
        int clen = d.deflate(comp);
        d.end();

        // Valid input from the start of the array
        Inflater inf = new Inflater();
        inf.setInput(comp, 0, clen);
        byte[] out = new byte[100];
        int n = inf.inflate(out);
        if (n != data.length || !Arrays.equals(Arrays.copyOf(out, n), data)) {
            throw new RuntimeException("unexpected inflate result at offset 0");
        }
        if (!inf.finished()) {
            throw new RuntimeException("inflater should be finished");
        }
        inf.end();

        // Valid input with a non-zero offset
        byte[] padded = new byte[clen + 5];
        System.arraycopy(comp, 0, padded, 3, clen);
        Inflater inf2 = new Inflater();
        inf2.setInput(padded, 3, clen);
        byte[] out2 = new byte[100];
        int n2 = inf2.inflate(out2);
        if (n2 != data.length || !Arrays.equals(Arrays.copyOf(out2, n2), data)) {
            throw new RuntimeException("unexpected inflate result at offset 3");
        }
        inf2.end();

        // Boundary: empty input at the end of the array is accepted
        Inflater inf3 = new Inflater();
        inf3.setInput(comp, comp.length, 0);
        if (inf3.getRemaining() != 0) {
            throw new RuntimeException("remaining should be 0 for empty input");
        }
        inf3.end();

        // Invalid arguments must throw ArrayIndexOutOfBoundsException
        expectAioobe(comp, -1, 1);
        expectAioobe(comp, 0, -1);
        expectAioobe(comp, comp.length, 1);
        expectAioobe(comp, 1, comp.length);
    }

    static void expectAioobe(byte[] b, int off, int len) {
        Inflater i = new Inflater();
        try {
            i.setInput(b, off, len);
            throw new RuntimeException("no exception for off=" + off + " len=" + len);
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } finally {
            i.end();
        }
    }
}
