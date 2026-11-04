/*
 * @test
 * @summary Behavioral checks for Deflater.deflate(byte[], int, int, int) argument validation and basic compression.
 * @run main DeflaterWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) throws Exception {
        testOffBeyondOutputLength();
        testNegativeOffset();
        testNegativeLength();
        testInvalidFlushMode();
        testRoundTripWithFinish();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOffBeyondOutputLength() {
        Deflater d = new Deflater();
        try {
            byte[] out = new byte[16];
            try {
                d.deflate(out, 16, 1, Deflater.NO_FLUSH);
                check(false, "expected ArrayIndexOutOfBoundsException for off + len > length");
            } catch (ArrayIndexOutOfBoundsException expected) {
                // expected
            }
        } finally {
            d.end();
        }
    }

    static void testNegativeOffset() {
        Deflater d = new Deflater();
        try {
            byte[] out = new byte[16];
            try {
                d.deflate(out, -1, 1, Deflater.NO_FLUSH);
                check(false, "expected ArrayIndexOutOfBoundsException for negative off");
            } catch (ArrayIndexOutOfBoundsException expected) {
                // expected
            }
        } finally {
            d.end();
        }
    }

    static void testNegativeLength() {
        Deflater d = new Deflater();
        try {
            byte[] out = new byte[16];
            try {
                d.deflate(out, 0, -1, Deflater.NO_FLUSH);
                check(false, "expected ArrayIndexOutOfBoundsException for negative len");
            } catch (ArrayIndexOutOfBoundsException expected) {
                // expected
            }
        } finally {
            d.end();
        }
    }

    static void testInvalidFlushMode() {
        Deflater d = new Deflater();
        try {
            byte[] out = new byte[16];
            try {
                d.deflate(out, 0, 16, 7);
                check(false, "expected IllegalArgumentException for invalid flush mode");
            } catch (IllegalArgumentException expected) {
                // expected
            }
        } finally {
            d.end();
        }
    }

    static void testRoundTripWithFinish() {
        Deflater d = new Deflater();
        try {
            byte[] input = "blahblahblah".getBytes(StandardCharsets.UTF_8);
            d.setInput(input);
            d.finish();
            byte[] out = new byte[256];
            int total = 0;
            int guard = 0;
            while (!d.finished() && guard++ < 100) {
                total += d.deflate(out, total, out.length - total, Deflater.NO_FLUSH);
            }
            check(d.finished(), "deflater should report finished after finish()");
            check(total > 0, "compressed output should not be empty");
            check(total <= out.length, "written count must not exceed buffer length");
        } finally {
            d.end();
        }
    }
}
