/* @test    @summary Test Collator.setDecomposition with valid and invalid modes    @run main CollatorWitupTest */

import java.text.Collator;

public class CollatorWitupTest {

    public static void main(String[] args) {
        testSetDecompositionValidModes();
        testSetDecompositionInvalidMode();
    }

    static void testSetDecompositionValidModes() {
        Collator collator = Collator.getInstance();
        try {
            collator.setDecomposition(Collator.NO_DECOMPOSITION);
            collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
        } catch (Exception e) {
            throw new AssertionError("Valid modes should not throw exception", e);
        }
    }

    static void testSetDecompositionInvalidMode() {
        Collator collator = Collator.getInstance();
        boolean exceptionThrown = false;
        try {
            collator.setDecomposition(-1); // invalid mode
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Setting invalid mode should throw IllegalArgumentException");
        }
    }
}
