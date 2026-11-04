/* @test    @summary Test for Deflater.setLevel(int) method    @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) {
        testSetLevelValidValues();
        testSetLevelInvalidValues();
    }

    static void testSetLevelValidValues() {
        try {
            Deflater deflater = new Deflater();
            for (int level = 0; level <= 9; level++) {
                deflater.setLevel(level);
            }
        } catch (Exception e) {
            throw new AssertionError("Valid levels should not throw exception", e);
        }
    }

    static void testSetLevelInvalidValues() {
        int[] invalidLevels = {-1, 10, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int invalidLevel : invalidLevels) {
            try {
                Deflater deflater = new Deflater();
                deflater.setLevel(invalidLevel);
                throw new AssertionError("Expected IllegalArgumentException for level: " + invalidLevel);
            } catch (IllegalArgumentException expected) {
                // expected exception
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception for level: " + invalidLevel, e);
            }
        }
    }
}
