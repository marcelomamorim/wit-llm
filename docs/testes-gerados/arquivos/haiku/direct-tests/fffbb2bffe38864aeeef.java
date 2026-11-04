/*
 * @test
 * @summary Verifies Deflater.setLevel accepts valid levels, rejects invalid ones, and affects deflate output
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) throws Exception {
        testValidLevelsAccepted();
        testInvalidLevelsRejected();
        testLevelZeroProducesLargerOutputThanLevelNine();
        testSetLevelAffectsSubsequentDeflate();
    }

    static void testValidLevelsAccepted() {
        Deflater d = new Deflater();
        try {
            d.setLevel(Deflater.DEFAULT_COMPRESSION);
            for (int level = 0; level <= 9; level++) {
                d.setLevel(level);
            }
        } finally {
            d.end();
        }
    }

    static void testInvalidLevelsRejected() {
        checkIllegalLevel(10);
        checkIllegalLevel(-2);
        checkIllegalLevel(Integer.MAX_VALUE);
        checkIllegalLevel(Integer.MIN_VALUE);
    }

    static void checkIllegalLevel(int level) {
        Deflater d = new Deflater();
        try {
            d.setLevel(level);
        } catch (IllegalArgumentException expected) {
            return;
        } finally {
            d.end();
        }
        throw new RuntimeException("IllegalArgumentException expected for level " + level);
    }

    static void testLevelZeroProducesLargerOutputThanLevelNine() {
        byte[] input = new byte[1000];
        java.util.Arrays.fill(input, (byte) 'a');

        int stored = compress(0, input);
        int best = compress(9, input);

        if (stored < input.length) {
            throw new RuntimeException("level 0 output too small: " + stored);
        }
        if (best >= stored) {
            throw new RuntimeException("level 9 should compress better: " + best + " vs " + stored);
        }
    }

    static void testSetLevelAffectsSubsequentDeflate() {
        byte[] input = new byte[1000];
        java.util.Arrays.fill(input, (byte) 'b');

        Deflater d = new Deflater(9);
        try {
            d.setLevel(0);
            d.setInput(input);
            d.finish();
            byte[] out = new byte[4096];
            int n = d.deflate(out);
            if (n < input.length) {
                throw new RuntimeException("setLevel(0) not applied, output length: " + n);
            }
        } finally {
            d.end();
        }
    }

    static int compress(int level, byte[] input) {
        Deflater d = new Deflater(level);
        try {
            d.setInput(input);
            d.finish();
            byte[] out = new byte[4096];
            return d.deflate(out);
        } finally {
            d.end();
        }
    }
}
