/* @test    @summary Test setting valid and invalid compression levels in Deflater    @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testSetLevelValidValues();
        testSetLevelInvalidValues();
    }

    static void testDefaultConstructor() {
        Deflater deflater = new Deflater();
        // Test default level is within valid range
        int level = deflater.getLevel();
        if (level < 0 || level > 9) {
            throw new AssertionError("Default level out of range: " + level);
        }
    }

    static void testSetLevelValidValues() {
        Deflater deflater = new Deflater();
        for (int level = 0; level <= 9; level++) {
            deflater.setLevel(level);
            int currentLevel = deflater.getLevel();
            if (currentLevel != level) {
                throw new AssertionError("Set level failed for: " + level + ", got: " + currentLevel);
            }
        }
        // Test with DEFAULT_COMPRESSION (-1)
        deflater.setLevel(Deflater.DEFAULT_COMPRESSION);
        if (deflater.getLevel() != Deflater.DEFAULT_COMPRESSION) {
            throw new AssertionError("Failed to set DEFAULT_COMPRESSION level");
        }
    }

    static void testSetLevelInvalidValues() {
        Deflater deflater = new Deflater();
        int[] invalidLevels = { -2, 10, 100, Integer.MAX_VALUE, Integer.MIN_VALUE };
        for (int invalidLevel : invalidLevels) {
            try {
                deflater.setLevel(invalidLevel);
                throw new AssertionError("Expected IllegalArgumentException for level: " + invalidLevel);
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
        }
    }
}
