/*
 * @test
 * @summary Test java.util.zip.Deflater.setLevel(int) method behavior
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) {
        testValidLevels();
        testInvalidLevels();
    }

    private static void testValidLevels() {
        Deflater def = new Deflater();

        // Test setting valid levels 0 through 9
        for (int level = 0; level <= 9; level++) {
            def.setLevel(level);
        }

        // Test setting DEFAULT_COMPRESSION (-1)
        def.setLevel(Deflater.DEFAULT_COMPRESSION);
    }

    private static void testInvalidLevels() {
        Deflater def = new Deflater();

        int[] invalidLevels = { -2, 10, Integer.MIN_VALUE, Integer.MAX_VALUE };

        for (int level : invalidLevels) {
            try {
                def.setLevel(level);
                throw new RuntimeException("Expected IllegalArgumentException for level: " + level);
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
        }
    }
}
