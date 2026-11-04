/*
 * @test
 * @summary Verifies argument validation of Deflater.deflate(byte[], int, int) and basic public behavior
 * @run main DeflaterWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflaterWitupTest {

    public static void main(String[] args) throws Exception {
        testInvalidArgumentsThrowAioobe();
        testRoundTripWithValidArguments();
        testZeroLengthAtEndOfArray();
    }

    static void testInvalidArgumentsThrowAioobe() {
        byte[] out = new byte[16];
        expectAioobe(out, -1, 4);
        expectAioobe(out, 0, -1);
        expectAioobe(out, 10, 10);
        expectAioobe(out, 17, 0);
        expectAioobe(out, 0, out.length + 1);
    }

    static void expectAioobe(byte[] out, int off, int len) {
        Deflater d = new Deflater();
        try {
            d.deflate(out, off, len);
            throw new AssertionError("expected ArrayIndexOutOfBoundsException for off="
                    + off + " len=" + len);
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            d.end();
        }
    }

    static void testRoundTripWithValidArguments() throws Exception {
        byte[] input = "blahblahblah".getBytes(StandardCharsets.UTF_8);
        byte[] compressed = new byte[100];

        Deflater d = new Deflater();
        d.setInput(input);
        d.finish();
        int n = d.deflate(compressed, 0, compressed.length);
        d.end();

        if (n <= 0) {
            throw new AssertionError("expected compressed output, got " + n);
        }

        Inflater inf = new Inflater();
        inf.setInput(compressed, 0, n);
        byte[] result = new byte[100];
        int r = inf.inflate(result, 0, result.length);
        inf.end();

        if (r != input.length) {
            throw new AssertionError("unexpected inflated length: " + r);
        }
        for (int i = 0; i < input.length; i++) {
            if (result[i] != input[i]) {
                throw new AssertionError("mismatch at index " + i);
            }
        }
    }

    static void testZeroLengthAtEndOfArray() {
        byte[] out = new byte[8];
        Deflater d = new Deflater();
        try {
            int n = d.deflate(out, out.length, 0);
            if (n != 0) {
                throw new AssertionError("expected 0 bytes, got " + n);
            }
        } finally {
            d.end();
        }
    }
}
