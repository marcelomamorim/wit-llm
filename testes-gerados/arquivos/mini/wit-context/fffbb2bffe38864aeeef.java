/*
 * @test
 * @summary Test java.util.zip.Deflater.setLevel(int) for valid and invalid compression levels
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) {
        testValidLevels();
        testInvalidLevels();
    }

    private static void testValidLevels() {
        Deflater deflater = new Deflater();
        // Test boundary valid levels 0 and 9
        deflater.setLevel(0);
        deflater.setLevel(9);
        // Test DEFAULT_COMPRESSION (-1) is accepted
        deflater.setLevel(-1);
        // Test a mid valid level
        deflater.setLevel(5);
    }

    private static void testInvalidLevels() {
        Deflater deflater = new Deflater();
        int[] invalidLevels = { -2, 10, 100, Integer.MIN_VALUE, Integer.MAX_VALUE };
        for (int level : invalidLevels) {
            try {
                deflater.setLevel(level);
                throw new RuntimeException("Expected IllegalArgumentException for level: " + level);
            } catch (IllegalArgumentException iae) {
                // Expected exception
            }
        }
    }
}
