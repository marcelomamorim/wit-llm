/* @test    @summary Test for Collator.setDecomposition with valid and invalid inputs    @run main CollatorWitupTest */

import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest {

    public static void main(String[] args) {
        testSetDecompositionValidModes();
        testSetDecompositionInvalidMode();
    }

    private static void testSetDecompositionValidModes() {
        Collator collator = Collator.getInstance(Locale.US);
        try {
            collator.setDecomposition(Collator.NO_DECOMPOSITION);
            collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
        } catch (Exception e) {
            throw new AssertionError("Valid modes should not throw exception", e);
        }
    }

    private static void testSetDecompositionInvalidMode() {
        Collator collator = Collator.getInstance(Locale.US);
        int invalidMode = 3; // invalid mode, not defined in Collator
        try {
            collator.setDecomposition(invalidMode);
            throw new AssertionError("Expected IllegalArgumentException for invalid mode");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
