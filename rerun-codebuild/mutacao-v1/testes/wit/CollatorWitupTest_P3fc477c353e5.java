/* @test    @summary Test for Collator.setDecomposition with valid and invalid inputs    @run main CollatorWitupTest_P3fc477c353e5 */

import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest_P3fc477c353e5 {

    public static void main(String[] args) {
        testSetDecompositionValidValues();
        testSetDecompositionInvalidValue();
    }

    private static void testSetDecompositionValidValues() {
        try {
            Collator collator = Collator.getInstance();
            // Valid values: NO_DECOMPOSITION, CANONICAL_DECOMPOSITION, FULL_DECOMPOSITION
            // Assuming these are 0, 1, 2 respectively, as per typical constants
            collator.setDecomposition(Collator.NO_DECOMPOSITION);
            collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid values: " + e);
        }
    }

    private static void testSetDecompositionInvalidValue() {
        Collator collator = Collator.getInstance();
        int invalidValue = -1;
        try {
            collator.setDecomposition(invalidValue);
            throw new AssertionError("Expected IllegalArgumentException for invalid value: " + invalidValue);
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
