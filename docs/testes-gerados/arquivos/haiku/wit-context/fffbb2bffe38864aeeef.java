/*
 * @test
 * @summary Verifies Deflater.setLevel accepts valid levels and rejects out-of-range levels with IllegalArgumentException
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) {
        int[] validLevels = {-1, 0, 1, 5, 9, Deflater.DEFAULT_COMPRESSION};
        for (int level : validLevels) {
            Deflater d = new Deflater();
            try {
                d.setLevel(level);
            } finally {
                d.end();
            }
        }

        int[] invalidLevels = {-2, 10, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int level : invalidLevels) {
            Deflater d = new Deflater();
            boolean thrown = false;
            try {
                d.setLevel(level);
            } catch (IllegalArgumentException e) {
                thrown = true;
            } finally {
                d.end();
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for level " + level);
            }
        }

        Deflater d = new Deflater();
        try {
            boolean thrown = false;
            try {
                d.setLevel(10);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for level 10");
            }
            d.setLevel(3);
            d.setInput(new byte[] {1, 2, 3, 4, 5});
            d.finish();
            byte[] out = new byte[64];
            int n = d.deflate(out);
            if (n <= 0) {
                throw new RuntimeException("Expected compressed output after valid setLevel");
            }
        } finally {
            d.end();
        }
    }
}
