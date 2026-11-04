/*
 * @test
 * @summary Test Collator.setDecomposition with valid and invalid inputs
 * @run main CollatorWitupTest
 */

import java.text.Collator;

public class CollatorWitupTest {
    public static void main(String[] args) {
        Collator collator = Collator.getInstance();

        // Test valid decomposition modes
        collator.setDecomposition(Collator.NO_DECOMPOSITION);
        collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
        collator.setDecomposition(Collator.FULL_DECOMPOSITION);

        // Test invalid decomposition mode triggers IllegalArgumentException
        try {
            collator.setDecomposition(-1);
            throw new RuntimeException("Expected IllegalArgumentException for decompositionMode -1");
        } catch (IllegalArgumentException expected) {
        }

        try {
            collator.setDecomposition(42);
            throw new RuntimeException("Expected IllegalArgumentException for decompositionMode 42");
        } catch (IllegalArgumentException expected) {
        }
    }
}
