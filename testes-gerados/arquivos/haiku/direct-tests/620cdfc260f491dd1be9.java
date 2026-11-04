/*
 * @test
 * @summary Verifies Deflater.setInput(byte[], int, int) argument validation and observable input consumption
 * @run main DeflaterWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflaterWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = "abcdefghij".getBytes(StandardCharsets.US_ASCII);
        testRoundTripWithOffset(data);
        testValidBoundaryArguments(data);
        testInvalidArguments(data);
    }

    static void testRoundTripWithOffset(byte[] data) throws Exception {
        Deflater def = new Deflater();
        try {
            def.setInput(data, 2, 3);
            def.finish();
            byte[] out = new byte[100];
            int n = def.deflate(out);
            check(def.getTotalIn() == 3, "getTotalIn should be 3");
            check(def.finished(), "deflater should be finished");

            Inflater inf = new Inflater();
            try {
                inf.setInput(out, 0, n);
                byte[] res = new byte[100];
                int m = inf.inflate(res);
                check(m == 3, "inflated length should be 3 but was " + m);
                check(Arrays.equals(Arrays.copyOfRange(res, 0, 3),
                                    Arrays.copyOfRange(data, 2, 5)),
                      "round trip content mismatch");
            } finally {
                inf.end();
            }
        } finally {
            def.end();
        }
    }

    static void testValidBoundaryArguments(byte[] data) {
        expectNoException(data, data.length, 0);
        expectNoException(data, 0, data.length);
        expectNoException(data, data.length - 1, 1);
        expectNoException(data, 0, 0);
    }

    static void testInvalidArguments(byte[] data) {
        expectAioobe(data, -1, 1);
        expectAioobe(data, 0, -1);
        expectAioobe(data, data.length, 1);
        expectAioobe(data, 8, 3);
        expectAioobe(data, data.length + 1, 0);
    }

    static void expectNoException(byte[] data, int off, int len) {
        Deflater def = new Deflater();
        try {
            def.setInput(data, off, len);
        } finally {
            def.end();
        }
    }

    static void expectAioobe(byte[] data, int off, int len) {
        Deflater def = new Deflater();
        boolean thrown = false;
        try {
            def.setInput(data, off, len);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        } finally {
            def.end();
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for off=" + off + " len=" + len);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
