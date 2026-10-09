/* @test    @summary Test Collator.setDecomposition with valid and invalid modes    @run main CollatorWitupTest_P3fc477c353e5 */

import java.text.Collator;

public class CollatorWitupTest_P3fc477c353e5 {

    public static void main(String[] args) {
        testSetDecompositionValidModes();
        testSetDecompositionInvalidMode();
    }

    private static void testSetDecompositionValidModes() {
        Collator collator = Collator.getInstance();
        try {
            collator.setDecomposition(Collator.NO_DECOMPOSITION);
            collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
        } catch (Exception e) {
            throw new AssertionError("Valid modes should not throw exception", e);
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
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Setting invalid mode did not throw IllegalArgumentException");
        }
    }
}
