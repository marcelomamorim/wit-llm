/* @test    @summary Test Collator.setDecomposition with valid and invalid inputs    @run main CollatorWitupTest_P390066c92789 */

import java.text.Collator;

public class CollatorWitupTest_P390066c92789 {

    public static void main(String[] args) {
        testSetDecompositionValidModes();
        testSetDecompositionInvalidMode();
    }

    private static void testSetDecompositionValidModes() {
        Collator collator = Collator.getInstance();
        int[] validModes = { Collator.NO_DECOMPOSITION, Collator.CANONICAL_DECOMPOSITION, Collator.FULL_DECOMPOSITION };
        for (int mode : validModes) {
            try {
                collator.setDecomposition(mode);
            } catch (Exception e) {
                throw new AssertionError("setDecomposition threw an exception for valid mode: " + mode, e);
            }
        }
    }

    private static void testSetDecompositionInvalidMode() {
        Collator collator = Collator.getInstance();
        int invalidMode = -1;
        boolean exceptionThrown = false;
        try {
            collator.setDecomposition(invalidMode);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid mode")
        }
    }
}
