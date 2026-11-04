/*
 * @test
 * @summary Test Collator.setDecomposition(int) for valid and invalid inputs
 * @run main CollatorWitupTest
 */

import java.text.Collator;

public class CollatorWitupTest {
    public static void main(String[] args) {
        Collator collator = Collator.getInstance();

        // Test valid decomposition modes
        int[] validModes = {
            0, // NO_DECOMPOSITION
            1, // CANONICAL_DECOMPOSITION
            2  // FULL_DECOMPOSITION
        };

        for (int mode : validModes) {
            collator.setDecomposition(mode);
            // No exception expected, verify by calling again with same mode
            collator.setDecomposition(mode);
        }

        // Test invalid decomposition modes
        int[] invalidModes = {
            -1, 3, 100, Integer.MIN_VALUE, Integer.MAX_VALUE
        };

        for (int mode : invalidModes) {
            boolean threw = false;
            try {
                collator.setDecomposition(mode);
            } catch (IllegalArgumentException e) {
                threw = true;
            }
            if (!threw) {
                throw new RuntimeException("Expected IllegalArgumentException for decompositionMode=" + mode);
            }
        }

        System.out.println("All tests passed.");
    }
}
